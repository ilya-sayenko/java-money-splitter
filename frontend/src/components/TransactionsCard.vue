<script setup lang="ts">
import {useRoute} from "vue-router";
import {computed, onMounted} from "vue";
import {usePartyStore} from "@/stores/partyStore.ts";
import {storeToRefs} from "pinia";
import {TransactionStatus} from "@/models/TransactionStatus.ts";
import type {Transaction} from "@/models/Transaction.ts";
import {TransactionUpdateRequest} from "@/http/data/models/TransactionUpdateRequest.ts";
import {useI18n} from "vue-i18n";

const { t, n } = useI18n();
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

function isPending(transaction: Transaction) {
  return transaction.status === TransactionStatus.PENDING;
}

function isClosed(transaction: Transaction) {
  return transaction.status === TransactionStatus.CLOSED;
}

async function updateTransaction(id: string, status: TransactionStatus) {
  const request = new TransactionUpdateRequest();
  request.id = id;
  request.status = status;
  await partyStore.updateTransaction(request);
  await partyStore.loadTransactionsByPartyId(partyId.value);
}

onMounted(async () => {
  await loadTransactions();
})
</script>

<template>
  <div class="card card-translated transactions-card">
    <h2>🧮 {{ t('headers.transactions') }}</h2>
    <p class="transactions-card-description">{{ t('transactionsCard.description') }}</p>

    <div v-if="showTransactions">
      <h3 class="transactions-list-title">{{ t('transactionsCard.debts') }}:</h3>

      <ul class="spendings-list">
        <li class="transaction-item" v-for="transaction in transactions" :key="transaction.id">
          <p v-if="isPending(transaction)">
            {{ t('transactionsCard.transactionPending', [transaction.payer.name, n(transaction.amount, 'currency') , transaction.payee.name]) }}
          </p>
          <p v-if="isClosed(transaction)"> {{ t('transactionsCard.transactionClosed', [transaction.payer.name, transaction.payee.name]) }}</p>
          <button v-if="isPending(transaction)" @click="updateTransaction(transaction.id, TransactionStatus.CLOSED)">✔️</button>
          <button v-if="isClosed(transaction)" @click="updateTransaction(transaction.id, TransactionStatus.PENDING)">❌</button>
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped>

</style>