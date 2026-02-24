<script setup lang="ts">
import {computed, reactive, ref, watch} from 'vue';
import {usePartyStore} from "@/stores/partyStore.js";
import {useRoute} from "vue-router";
import {storeToRefs} from "pinia";
import {SpendingCreateRequest} from "@/http/data/models/SpendingCreateRequest.js";
import {SplitRequest} from "@/http/data/models/SplitRequest.js";
import {SplitType} from "@/models/SplitType.js";
import {useI18n} from "vue-i18n";
import {helpers, numeric, required} from "@vuelidate/validators";
import useVuelidate from "@vuelidate/core";
import {errorMessage} from "@/utils/errorMessage.ts";

const props = defineProps<{
  isOpened: boolean
}>();

const emits = defineEmits<{
  (e: 'close'): void
}>();

const { t, n } = useI18n();
const route = useRoute();
const partyId = computed(() => route.params.partyId as string);
const partyStore = usePartyStore();
const { participants } = storeToRefs(partyStore);

const proportions = ref<Record<string, number>>({});

const proportionsValidity = computed<Record<string, boolean>>(() => {
  const result: Record<string, boolean> = {};
  if (!participants.value) {
    return {};
  }
  participants.value.forEach((participant) => {
    if (!selectedParticipants.value.includes(participant.id)) {
      result[participant.id] = true;
    } else {
      result[participant.id] = (proportions.value[participant.id] || 0) <= (formState.spendingAmount || 0)
    }
  });

  return result;
});

const participantsIds = computed(() =>
  participants.value ? participants.value.map((participant) => participant.id) : []);

const selectedParticipants = ref<string[]>([]);

const proportionsPartitionsAmount = computed(() => {
  let partitions = 0;
  let result : Record<string, number> = {};
  Object.values(proportions.value).forEach((value: number) => partitions += value);
  const perParticipant = selectedParticipants.value ? (formState.spendingAmount || 0) / partitions : 0;
  Object.entries(proportions.value).forEach(([key, value]) => result[key] = value *  perParticipant);

  return result;
});

const isShowProportions = ref(false);

interface FormData {
  spendingName: string;
  spendingAmount: number | null;
  spendingPayerId: string;
  spendingSplitType: SplitType;
  spendingProportions: boolean;
}

const formState = reactive<FormData>({
  spendingName: '',
  spendingAmount: null,
  spendingPayerId: '',
  spendingSplitType: SplitType.EQUAL,
  spendingProportions: true
})

const rules = computed(() => ({
  spendingName: {
    required: helpers.withMessage(t('errors.spendingNameRequired'), required)
  },
  spendingAmount: {
    required: helpers.withMessage(t('errors.amountRequired'), required),
    numeric: helpers.withMessage(t('errors.amountDecimal'), numeric)
  },
  spendingPayerId: {
    required: helpers.withMessage(t('errors.payerRequired'), required)
  },
  spendingSplitType: {
    required: helpers.withMessage(t('errors.splitTypeRequired'), required)
  },
  spendingProportions: {
    isValid: () => Object.values(proportionsValidity.value).reduce((a, b) => a && b, true)
  }
}));

const v$ = useVuelidate(rules, formState);

const closeModal = () => {
  formState.spendingAmount = 0;
  formState.spendingSplitType = SplitType.EQUAL;
  formState.spendingName = '';
  formState.spendingPayerId = '';
  isShowProportions.value = false;

  proportions.value = {};
  selectedParticipants.value = participants.value ? participants.value.map((participant) => participant.id) : [];
  v$.value.$reset();
  emits('close');
};

watch(() => formState.spendingSplitType, () => recalculateProportions());

watch(() => formState.spendingAmount, () => recalculateProportions());

watch(selectedParticipants, () => recalculateProportions());

watch(() => props.isOpened, (isOpened) => {
  if (isOpened) {
    selectedParticipants.value = participantsIds.value;
    formState.spendingSplitType = SplitType.EQUAL;
    isShowProportions.value = false;
  }
});

watch(participantsIds, (newIds) => {
  if (props.isOpened && newIds.length > 0) {
    selectedParticipants.value = newIds;
  }
});

watch(() => formState.spendingAmount, (newAmount) => {
  if (newAmount === null) {
    formState.spendingAmount = null;
  }
})

function recalculateProportions() {
  if (!participants.value) {
    return;
  }

  proportions.value = {};

  switch (formState.spendingSplitType) {
    case SplitType.AMOUNT: {
      const total = formState.spendingAmount || 0;
      const count = selectedParticipants.value.length || 1;
      const perParticipant = total / count;

      for (const participant of participants.value) {
        if (selectedParticipants.value.includes(participant.id)) {
          proportions.value[participant.id] = Number(perParticipant.toFixed(2));
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

function recalculateAmountProportions(participantId: string) {
  if (!formState.spendingAmount || !participants.value) {
    return;
  }
  const remainder = formState.spendingAmount - (proportions.value[participantId] || 0);
  const count = (selectedParticipants.value.length || 1) - 1;
  const perParticipant = remainder / count;
  for (const participant of participants.value) {
    if (selectedParticipants.value.includes(participant.id) && participant.id !== participantId) {
      proportions.value[participant.id] = perParticipant > 0 ? perParticipant : 0;
    }
  }
}

function updateSpendingSplitType(splitType: SplitType) {
  formState.spendingSplitType = splitType;
}

async function createSpending() {
  const isFormValid = await v$.value.$validate();

  if (!isFormValid) {
    return;
  }

  const spending = new SpendingCreateRequest();
  spending.partyId = partyId.value;
  spending.payerId = formState.spendingPayerId;
  spending.name = formState.spendingName;
  spending.amount = formState.spendingAmount!;

  const split = new SplitRequest();
  split.splitType = formState.spendingSplitType as SplitType;
  split.participants = proportions.value;
  spending.split = split;

  await partyStore.createSpending(spending);
  await Promise.all([
    partyStore.loadSpendingsByPartyId(partyId.value),
    partyStore.loadTransactionsByPartyId(partyId.value)
  ]);
  closeModal();
}

// function isIncorrectProportion(participantId: string) {
//   return (proportions.value[participantId] || 0) > (formState.spendingAmount || 0);
// }
</script>

<template>
  <Teleport to="body">
    <div v-show="isOpened" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <button class="modal-close-button" @click="closeModal">×</button>
        <h2>{{ t('headers.createSpending') }}</h2>

        <div class="form-group">
          <label for="participants">{{ t('labels.payer') }}</label>
          <select name="participants" id="participants" v-model="formState.spendingPayerId" @input="v$.spendingPayerId.$reset()">
            <option
              v-for="participant in participants"
              :key="participant.id"
              :value="participant.id"
            >{{ participant.name }}</option>
          </select>
          <small class="error" v-if="v$.spendingPayerId.$error">{{ errorMessage(v$.spendingPayerId.$errors) }}</small>
        </div>

        <div class="form-group">
          <label for="new-spending-name">{{ t('labels.spendingName') }}</label>
          <input type="text" id="new-spending-name" v-model="formState.spendingName" @input="v$.spendingName.$reset()" />
          <small class="error" v-if="v$.spendingName.$error">{{ errorMessage(v$.spendingName.$errors) }}</small>
        </div>

        <div class="form-group">
          <label for="new-spending-amount">{{ t('labels.amount') }}:</label>
          <input type="number" id="new-spending-amount" v-model="formState.spendingAmount" @input="v$.spendingAmount.$reset()" />
          <small class="error" v-if="v$.spendingAmount.$error">{{ errorMessage(v$.spendingAmount.$errors) }}</small>
        </div>

        <div class="form-group-radio">
          <input
            type="radio"
            name="partitionType1"
            id="equal"
            :checked="formState.spendingSplitType === SplitType.EQUAL"
            @click="
              isShowProportions = false;
              updateSpendingSplitType(SplitType.EQUAL);
            "
          >
          <label for="equal">{{ t('radio.equal') }}</label>
        </div>

        <div class="form-group-radio">
          <input
            type="radio"
            id="notEqual"
            name="partitionType1"
            :checked="formState.spendingSplitType !== SplitType.EQUAL"
            @click="
              isShowProportions = true;
              updateSpendingSplitType(SplitType.AMOUNT)
            "
          >
          <label for="notEqual">{{ t('radio.notEqual') }}</label><br>
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
          <label for="tab1" class="tab-label">{{ t('splitType.amount') }}</label>

          <input
            type="radio"
            name="partitionType"
            id="tab2"
            class="tab-input"
            @click="updateSpendingSplitType(SplitType.PARTITION)"
          >
          <label for="tab2" class="tab-label">{{ t('splitType.partition') }}</label>

          <div class="tab-content tab-content-1">
            <ul id="new-spending-partitions">
              <li v-for="participant in participants" :key="participant.id">
                <div class="partition-data">

                  <div class="form-group-checkbox">
                    <input type="checkbox" :value="participant.id" v-model="selectedParticipants">
                    <label class="partition-participant-name">{{ participant.name }}</label>
                  </div>

                  <div class="form-group">
                    <input
                      type="number"
                      v-model="proportions[participant.id]"
                      @input="recalculateAmountProportions(participant.id)"
                    />
                    <span class="error" v-if="!proportionsValidity[participant.id]">{{ t('errors.incorrectProportion') }}</span>
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
                    <span>= {{ n(proportionsPartitionsAmount[participant.id] || 0, 'currency') }}</span>
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </div>
        <button class="btn btn-main" @click="createSpending">{{ t('buttons.addSpending') }}</button>
      </div>
    </div>
  </Teleport>
</template>

<style scoped>
</style>
