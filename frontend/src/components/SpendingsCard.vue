<script setup lang="ts">
import {useRoute} from "vue-router";
import {computed, onMounted, ref} from "vue";
import {usePartyStore} from "@/stores/partyStore.ts";
import {storeToRefs} from "pinia";
import {SpendingCreateRequest} from "@/http/data/models/SpendingCreateRequest.ts";
import {SplitRequest} from "@/http/data/models/SplitRequest.ts";
import {SplitType} from "@/models/SplitType.ts";

const route = useRoute();
const partyId = computed(() => route.params.partyId as string);
const partyStore = usePartyStore();
const { spendings, participants } = storeToRefs(partyStore);

const spendingName = ref('');
const spendingAmount = ref();
const spendingPayerId = ref('');

const showSpendings = computed(() => {
  return spendings.value && spendings.value.length !== 0;
})

async function createSpending() {
  const spending = new SpendingCreateRequest();
  spending.partyId = partyId.value;
  spending.payerId = spendingPayerId.value;
  spending.name = spendingName.value;
  spending.amount = spendingAmount.value;
  const split = new SplitRequest();
  split.splitType = SplitType.EQUAL;
  spending.split = split;
  await partyStore.createSpending(spending);
  await Promise.all([
    partyStore.loadSpendingsByPartyId(partyId.value),
    partyStore.loadTransactionsByPartyId(partyId.value)
  ]);
}

async function deleteSpendingById(id: string) {
  await partyStore.deleteSpendingById(id);
  await Promise.all([
    partyStore.loadSpendingsByPartyId(partyId.value),
    partyStore.loadTransactionsByPartyId(partyId.value)
  ]);
}

onMounted(async () => {
  await partyStore.loadSpendingsByPartyId(partyId.value);
})
</script>

<template>
  <div class="card card-translated spendings-card">
    <h2>🧾 Расходы</h2>

    <div class="form-group">
      <label for="new-spending-name">За что платили?</label>
      <input type="text" id="new-spending-name" placeholder="Например, Аренда лыж" v-model="spendingName" />
    </div>

    <div class="form-group">
      <label for="new-spending-amount">Сумма:</label>
      <input type="text" id="new-spending-amount" placeholder="5000" v-model="spendingAmount" />
    </div>

    <div class="form-group">
      <label for="new-spending-amount">Кто платил?</label>
      <select name="participants" id="participants" v-model="spendingPayerId">
        <option
          v-for="participant in participants"
          :value="participant.id"
        >{{ participant.name }}</option>
      </select>
    </div>

    <button class="btn btn-main" @click="createSpending">Добавить расход</button>

    <div v-if="showSpendings">
      <h3 class="spendings-list-title">Список расходов:</h3>
      <ul class="spendings-list">
        <li class="spending-item" v-for="spending in spendings">
          <div class="spending-item-info">
            <div class="spending-name">{{ spending.name }}</div>
            <div>Оплатил(а): {{ spending.payer.name }}</div>
            <div>Сумма: <span class="spending-amount">{{ spending.amount }}</span></div>
          </div>
          <div>
<!--            <button class="btn-edit">✏️</button>-->
            <button @click="deleteSpendingById(spending.id)">❌</button>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped>

</style>