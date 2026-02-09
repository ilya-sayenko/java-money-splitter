<script setup lang="ts">
import {computed, onMounted, reactive, ref, watch} from 'vue';
import {usePartyStore} from "@/stores/partyStore.js";
import {useRoute} from "vue-router";
import {storeToRefs} from "pinia";
import {SpendingCreateRequest} from "@/http/data/models/SpendingCreateRequest.js";
import {SplitRequest} from "@/http/data/models/SplitRequest.js";
import {SplitType} from "@/models/SplitType.js";

defineProps<{
  isOpened: boolean
}>();

const emits = defineEmits<{
  (e: 'close'): void
}>();

const route = useRoute();
const partyId = computed(() => route.params.partyId as string);

const spendingName = ref('');
const spendingAmount = ref();
const spendingPayerId = ref('');
const spendingSplitType = ref<SplitType>(SplitType.EQUAL);
const partyStore = usePartyStore();
const { participants } = storeToRefs(partyStore);
const proportionsObject = ref<Record<string, number>>({});
const participantsIds = computed(() =>
  participants.value ? participants.value.map((participant) => participant.id) : []);
const selectedParticipants = ref<string[]>(participantsIds.value);
// const selectedParticipants = reactive(new Set<string>());

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

// const splitTypes = computed(() =>
//   Object.values(SplitType).filter(value => typeof value === 'number') as SplitType[]
// );

// const isShowProportions = computed(() =>
//   [SplitType.PARTITION, SplitType.AMOUNT].includes(spendingSplitType.value as SplitType)
// );

const isShowProportions = ref(false);

const closeModal = () => {
  proportionsObject.value = {};
  spendingSplitType.value = SplitType.EQUAL;
  selectedParticipants.value = participants.value ? participants.value.map((participant) => participant.id) : [];
  emits('close');
};

// onMounted(() => {
//   console.log(participantsIds.value);
//   console.log(selectedParticipants.value);
// })

watch(spendingSplitType, () => {
  recalculateProportions();
});

watch(spendingAmount, () => {
  recalculateProportions();
})

function recalculateProportions() {
  if (!participants.value) {
    return;
  }

  switch (spendingSplitType.value) {
    case SplitType.AMOUNT: {
      const total = Number(spendingAmount.value) || 0;
      const count = participants.value.length || 1;
      const perParticipant = count ? total / count : 0;
      participants.value
        .filter((participant) => selectedParticipants.value.includes(participant.id))
        .forEach((participant) => proportionsObject.value[participant.id] = perParticipant);
      break;
    }

    case SplitType.PARTITION: {
      participants.value
        .filter((participant) => selectedParticipants.value.includes(participant.id))
        .forEach((participant) => proportionsObject.value[participant.id] = 1);
      break;
    }
  }
}

// watch(spendingAmount, (newValue) => {
//   proportions.clear();
//
//   debugger
//
//   if (!participants.value) {
//     return;
//   }
//
//   switch (spendingSplitType.value) {
//     case SplitType.AMOUNT: {
//       const total = Number(newValue) || 0;
//       const count = participants.value.length || 1;
//       const perParticipant = count ? total / count : 0;
//       participants.value
//         .filter((participant) => selectedParticipants.value.includes(participant.id))
//         .forEach((participant) => proportions.set(participant.id, perParticipant));
//       break;
//     }
//
//     case SplitType.PARTITION: {
//       participants.value
//         .filter((participant) => selectedParticipants.value.includes(participant.id))
//         .forEach((participant) => proportions.set(participant.id, 1));
//       break;
//     }
//   }
//
//   console.log(proportions);
// })
//
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
  split.participants = proportionsObject.value;
  spending.split = split;

  await partyStore.createSpending(spending);
  await Promise.all([
    partyStore.loadSpendingsByPartyId(partyId.value),
    partyStore.loadTransactionsByPartyId(partyId.value)
  ]);
  closeModal();
}

// TODO teleport
</script>

<template>
  <Teleport to="body">
    <div v-show="isOpened" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <button class="modal-close-button" @click="closeModal">×</button>
        <h2>Создать расход</h2>

        <div class="form-group">
          <label for="participants">Кто платил?</label>
          <select name="participants" id="participants" v-model="spendingPayerId">
            <option
              v-for="participant in participants"
              :key="participant.id"
              :value="participant.id"
            >{{ participant.name }}</option>
          </select>
        </div>

        <div class="form-group">
          <label for="new-spending-name">За что?</label>
          <input type="text" id="new-spending-name" placeholder="Например, Аренда лыж" v-model="spendingName" />
        </div>

        <div class="form-group">
          <label for="new-spending-amount">Сумма:</label>
          <input type="text" id="new-spending-amount" placeholder="5000" v-model="spendingAmount" />
        </div>

        <div class="form-group-radio">
          <input
            type="radio"
            name="partitionType1"
            id="equal"
            @click="
            isShowProportions = false;
            updateSpendingSplitType(SplitType.EQUAL);"
            checked
          >
          <label for="equal">Разделить поровну между всеми</label>
        </div>

        <div class="form-group-radio">
          <input
            type="radio"
            id="notEqual"
            name="partitionType1"
            :value="true"
            v-model="isShowProportions"
            @click="updateSpendingSplitType(SplitType.AMOUNT)">
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
          <label for="tab1" class="tab-label">Сумма</label>

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
                      v-model="proportionsObject[participant.id]"
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
                      v-model="proportionsObject[participant.id]"
                    />
                    <span>= 12d3 р</span>
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </div>


        <!--      <div class="form-group" v-if="isShowProportions">-->
        <!--        <label for="new-spending-partitions">Партиции:</label>-->
        <!--        <ul id="new-spending-partitions">-->
        <!--          <li v-for="participant in participants" :key="participant.id">-->
        <!--            <div class="card partition-data">-->
        <!--              <span class="partition-participant-name">{{ participant.name }}</span>-->
        <!--              <input-->
        <!--                class="partition-amount"-->
        <!--                type="number"-->
        <!--                :value="proportions.get(participant.id)"-->
        <!--                @input="updateProportion(participant.id, $event)"-->
        <!--              />-->
        <!--            </div>-->
        <!--          </li>-->
        <!--        </ul>-->
        <!--      </div>-->
        <button class="btn btn-main" @click="createSpending">Добавить расход</button>
      </div>
    </div>
  </Teleport>
</template>

<style scoped>
</style>
