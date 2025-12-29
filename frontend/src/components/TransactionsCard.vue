<script setup lang="ts">
import {useRoute} from "vue-router";
import {computed, onMounted} from "vue";
import {usePartyStore} from "@/stores/partyStore.ts";
import {storeToRefs} from "pinia";

const route = useRoute();
const partyId = computed(() => route.params.partyId as string);
const partyStore = usePartyStore();
const { transactions } = storeToRefs(partyStore);

onMounted(async () => {
  await partyStore.loadTransactionsByPartyId(partyId.value);
})
</script>

<template>
  <div class="card transactions-card">
    <h2>🧮 Кто кому должен?</h2>
    <p class="transactions-card-description">Здесь будет показан расчёт задолженностей.</p>

    <button class="btn btn-secondary">Расчет задолженностей</button>

    <h3 class="transactions-list-title">Для погашения долгов нужно сделать переводы:</h3>

    <ul class="spendings-list">
      <li class="transaction-item" v-for="transaction in transactions" :key="transaction.id">
        <p>{{ transaction.payer.name }} -> {{ transaction.payee.name }}: <span class="transaction-amount">{{ transaction.amount }} р.</span></p>
      </li>
    </ul>
  </div>
</template>

<style scoped>

</style>