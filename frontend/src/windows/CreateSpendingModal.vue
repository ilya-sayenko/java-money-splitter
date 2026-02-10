<script setup lang="ts">
import {computed, ref, watch} from 'vue';
import {usePartyStore} from "@/stores/partyStore.js";
import {useRoute} from "vue-router";
import {storeToRefs} from "pinia";
import {SpendingCreateRequest} from "@/http/data/models/SpendingCreateRequest.js";
import {SplitRequest} from "@/http/data/models/SplitRequest.js";
import {SplitType} from "@/models/SplitType.js";
import {currencyFormat} from "@/utils/currencyFormat.ts";
import {useI18n} from "vue-i18n";

const props = defineProps<{
  isOpened: boolean
}>();

const emits = defineEmits<{
  (e: 'close'): void
}>();

const { t } = useI18n();
const route = useRoute();
const partyId = computed(() => route.params.partyId as string);

const spendingName = ref('');
const spendingAmount = ref();
const spendingPayerId = ref('');
const spendingSplitType = ref<SplitType>(SplitType.EQUAL);
const partyStore = usePartyStore();
const { participants } = storeToRefs(partyStore);
const proportions = ref<Record<string, number>>({});

const participantsIds = computed(() =>
  participants.value ? participants.value.map((participant) => participant.id) : []);
const selectedParticipants = ref<string[]>([]);

const proportionsPartitionsAmount = computed(() => {
  let partitions = 0;
  let result : Record<string, number> = {};
  Object.values(proportions.value).forEach((value: number) => partitions += value);
  const perParticipant = selectedParticipants.value ? spendingAmount.value / partitions : 0;
  Object.entries(proportions.value).forEach(([key, value]) => result[key] = value *  perParticipant);

  return result;
});

const isShowProportions = ref(false);

const closeModal = () => {
  spendingAmount.value = 0;
  spendingSplitType.value = SplitType.EQUAL;
  spendingName.value = '';
  spendingPayerId.value = '';
  isShowProportions.value = false;

  proportions.value = {};
  selectedParticipants.value = participants.value ? participants.value.map((participant) => participant.id) : [];
  emits('close');
};

watch(spendingSplitType, () => {
  recalculateProportions();
});

watch(spendingAmount, () => {
  recalculateProportions();
});

watch(selectedParticipants, () => {
  recalculateProportions();
});

watch(() => props.isOpened, (isOpened) => {
  if (isOpened) {
    selectedParticipants.value = participantsIds.value;
    spendingSplitType.value = SplitType.EQUAL;
    isShowProportions.value = false;
  }
});

watch(participantsIds, (newIds) => {
  if (props.isOpened && newIds.length > 0) {
    selectedParticipants.value = newIds;
  }
});

function recalculateProportions() {
  if (!participants.value) {
    return;
  }

  proportions.value = {};

  switch (spendingSplitType.value) {
    case SplitType.AMOUNT: {
      const total = spendingAmount.value || 0;
      const count = selectedParticipants.value.length || 1;
      const perParticipant = count ? total / count : 0;

      for (const participant of participants.value) {
        if (selectedParticipants.value.includes(participant.id)) {
          proportions.value[participant.id] = perParticipant;
        }
      }
      break;
    }

    case SplitType.PARTITION: {
      for (const participant of participants.value) {
        if (selectedParticipants.value.includes(participant.id)) {
          proportions.value[participant.id] = 1;
        }
      }
      break;
    }
  }
}

function updateSpendingSplitType(splitType: SplitType) {
  spendingSplitType.value = splitType;
}

async function createSpending() {
  const spending = new SpendingCreateRequest();
  spending.partyId = partyId.value;
  spending.payerId = spendingPayerId.value;
  spending.name = spendingName.value;
  spending.amount = spendingAmount.value;

  const split = new SplitRequest();
  split.splitType = spendingSplitType.value as SplitType;
  split.participants = proportions.value;
  spending.split = split;

  await partyStore.createSpending(spending);
  await Promise.all([
    partyStore.loadSpendingsByPartyId(partyId.value),
    partyStore.loadTransactionsByPartyId(partyId.value)
  ]);
  closeModal();
}

</script>

<template>
  <Teleport to="body">
    <div v-show="isOpened" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <button class="modal-close-button" @click="closeModal">×</button>
        <h2>Создать расход</h2>

        <div class="form-group">
          <label for="participants">{{ t('labels.payer') }}</label>
          <select name="participants" id="participants" v-model="spendingPayerId">
            <option
              v-for="participant in participants"
              :key="participant.id"
              :value="participant.id"
            >{{ participant.name }}</option>
          </select>
        </div>

        <div class="form-group">
          <label for="new-spending-name">{{ t('labels.spendingName') }}:</label>
          <input type="text" id="new-spending-name" placeholder="Например, Аренда лыж" v-model="spendingName" />
        </div>

        <div class="form-group">
          <label for="new-spending-amount">{{ t('labels.amount') }}:</label>
          <input type="text" id="new-spending-amount" placeholder="5000" v-model="spendingAmount" />
        </div>

        <div class="form-group-radio">
          <input
            type="radio"
            name="partitionType1"
            id="equal"
            :checked="spendingSplitType === SplitType.EQUAL"
            @click="
              isShowProportions = false;
              updateSpendingSplitType(SplitType.EQUAL);
            "
          >
          <label for="equal">Разделить поровну между всеми</label>
        </div>

        <div class="form-group-radio">
          <input
            type="radio"
            id="notEqual"
            name="partitionType1"
            :checked="spendingSplitType !== SplitType.EQUAL"
            @click="
              isShowProportions = true;
              updateSpendingSplitType(SplitType.AMOUNT)
            "
          >
          <label for="notEqual">Разделить по-другому</label><br>
        </div>


        <div class="tabs" v-if="isShowProportions">
          <input
            type="radio"
            name="partitionType"
            id="tab1"
            class="tab-input"
            checked
            @click="updateSpendingSplitType(SplitType.AMOUNT)"
          >
          <label for="tab1" class="tab-label">{{ t('labels.amount') }}</label>

          <input
            type="radio"
            name="partitionType"
            id="tab2"
            class="tab-input"
            @click="updateSpendingSplitType(SplitType.PARTITION)"
          >
          <label for="tab2" class="tab-label">В пропорции</label>

          <div class="tab-content tab-content-1">
            <ul id="new-spending-partitions">
              <li v-for="participant in participants" :key="participant.id">
                <div class="partition-data">

                  <div class="form-group-checkbox">
                    <input type="checkbox" :value="participant.id" v-model="selectedParticipants">
                    <label class="partition-participant-name">{{ participant.name }}</label>
                  </div>

                  <div class="form-group-checkbox">
                    <input
                      type="number"
                      v-model="proportions[participant.id]"
                    />
                  </div>
                </div>
              </li>
            </ul>
          </div>

          <div class="tab-content tab-content-2">
            <ul id="new-spending-partitions">
              <li v-for="participant in participants" :key="participant.id">
                <div class="partition-data">

                  <div class="form-group-checkbox">
                    <input type="checkbox" :value="participant.id" v-model="selectedParticipants">
                    <label class="partition-participant-name">{{ participant.name }}</label>
                  </div>

                  <div class="form-group-checkbox form-group-checkbox-justify">
                    <input
                      class="partition-amount"
                      type="number"
                      v-model="proportions[participant.id]"
                    />
                    <span>= {{ currencyFormat(proportionsPartitionsAmount[participant.id] || 0) }}</span>
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </div>
        <button class="btn btn-main" @click="createSpending">Добавить расход</button>
      </div>
    </div>
  </Teleport>
</template>

<style scoped>
</style>
