<script setup lang="ts">
import {defineProps, defineEmits, ref, computed, watch, reactive} from 'vue';
import {usePartyStore} from "@/stores/partyStore.js";
import {useRoute} from "vue-router";
import {storeToRefs} from "pinia";
import {SpendingCreateRequest} from "@/http/data/models/SpendingCreateRequest.js";
import {SplitRequest} from "@/http/data/models/SplitRequest.js";
import {SplitType} from "@/models/SplitType.js";

const props = defineProps({
  isOpened: Boolean
});

const emits = defineEmits(['close']);

const closeModal = () => {
  proportions.clear();
  emits('close');
};

const route = useRoute();
const partyId = computed(() => route.params.partyId as string);

const spendingName = ref('');
const spendingAmount = ref();
const spendingPayerId = ref('');
const spendingSplitType = ref<SplitType>();
const partyStore = usePartyStore();
const { participants } = storeToRefs(partyStore);
const proportions = reactive(new Map<string, number>());


// const proportions = computed(() => {
//   let proportionsMap = new Map();
//   if (participants.value) {
//     participants.value.forEach((participant) => {
//       proportionsMap.set(participant.id, 0);
//     });
//   }
//   return proportionsMap;
// })

// const partitions = computed(() => {
//   let partitionsMap = [];
//   if (participants.value) {
//     participants.value.forEach((participant) => {
//       new Partition
//       partitionsMap.push();
//     });
//   }
//   return partitionsMap;
// })

const splitTypes = computed(() =>
  Object.values(SplitType).filter(value => typeof value === 'number') as SplitType[]
);

const isShowPartitions = computed(() =>
  [SplitType.PARTITION, SplitType.AMOUNT].includes(spendingSplitType.value as SplitType)
);

watch(spendingSplitType, (newType) => {
  proportions.clear();

  if (!participants.value) {
    return;
  }

  switch (newType) {
    case SplitType.AMOUNT: {
      const total = Number(spendingAmount.value) || 0;
      const count = participants.value.length || 1;
      const perParticipant = count ? total / count : 0;

      participants.value.forEach((participant) => {
        proportions.set(participant.id, perParticipant);
      });
      break;
    }

    case SplitType.PARTITION: {
      participants.value.forEach((participant) => {
        proportions.set(participant.id, 1);
      });
      break;
    }
  }
});

// function updateProportion(participantId: string, event: Event) {
//   const target = event.target as HTMLInputElement;
//
//   const value = Number(target.value);
//   if (Number.isNaN(value)) {
//     return;
//   }
//
//   proportions.set(participantId, value);
// }

async function createSpending() {
  const spending = new SpendingCreateRequest();
  spending.partyId = partyId.value;
  spending.payerId = spendingPayerId.value;
  spending.name = spendingName.value;
  spending.amount = spendingAmount.value;

  const split = new SplitRequest();
  split.splitType = spendingSplitType.value as SplitType;
  split.participants = Object.fromEntries(proportions.entries());
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
  <div v-if="isOpened" class="modal-overlay" @click.self="closeModal">
    <div class="modal-content">
      <button class="modal-close-button" @click="closeModal">×</button>
      <h2>Создать расход</h2>

      <div class="form-group">
        <label for="new-spending-name">За что платили?</label>
        <input type="text" id="new-spending-name" placeholder="Например, Аренда лыж" v-model="spendingName" />
      </div>

      <div class="form-group">
        <label for="new-spending-amount">Сумма:</label>
        <input type="text" id="new-spending-amount" placeholder="5000" v-model="spendingAmount" />
      </div>

      <div class="form-group">
        <label for="participants">Кто платил?</label>
        <select name="participants" id="participants" v-model="spendingPayerId">
          <option
            v-for="participant in participants"
            :value="participant.id"
          >{{ participant.name }}</option>
        </select>
      </div>

      <div class="form-group">
        <label for="new-spending-type">Тип:</label>
        <select name="new-spending-type" id="new-spending-type" v-model="spendingSplitType">
          <option
            v-for="splitType in splitTypes"
            :key="splitType"
            :value="splitType"
          >{{ SplitType[splitType] }}</option>
        </select>
      </div>

      <div class="form-group" v-if="isShowPartitions">
        <label for="new-spending-partitions">Партиции:</label>
        <ul id="new-spending-partitions">
          <li v-for="participant in participants" :key="participant.id">
            <div class="card partition-data">
              <span class="partition-participant-name">{{ participant.name }}</span>
              <input
                class="partition-amount"
                type="number"
                :value="proportions.get(participant.id)"
              />
            </div>
          </li>
        </ul>
      </div>
      <button class="btn btn-main" @click="createSpending">Добавить расход</button>
    </div>
  </div>
</template>

<style scoped>
</style>
