<script setup lang="ts">
import {useRoute} from "vue-router";
import {computed, onMounted} from "vue";
import {usePartyStore} from "@/stores/partyStore.ts";
import {storeToRefs} from "pinia";

const route = useRoute();
const partyId = computed(() => route.params.partyId as string);
const partyStore = usePartyStore();
const { transactions } = storeToRefs(partyStore);
const showTransactions = computed(() => {
  return transactions.value && transactions.value.length !== 0;
});

async function loadTransactions() {
  await partyStore.loadTransactionsByPartyId(partyId.value);
}

onMounted(async () => {
  await loadTransactions();
})
</script>

<template>
  <div class="card card-translated transactions-card">
    <h2>🧮 Кто кому должен?</h2>
    <p class="transactions-card-description">Здесь будет показан расчёт задолженностей.</p>

    <div v-if="showTransactions">
      <h3 class="transactions-list-title">Для погашения долгов нужно сделать переводы:</h3>

      <ul class="spendings-list">
        <li class="transaction-item" v-for="transaction in transactions" :key="transaction.id">
          <p>{{ transaction.payer.name }} -> {{ transaction.payee.name }}: <span class="transaction-amount">{{ transaction.amount }} р.</span></p>
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped>

</style>