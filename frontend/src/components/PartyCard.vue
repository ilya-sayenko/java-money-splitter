<script setup lang="ts">
import {dateFormat} from "@/utils/dateFormat.ts";
import type {PartyWithAggregates} from "@/models/PartyWithAggregates.ts";
import {currencyFormat} from "@/utils/currencyFormat.ts";
import {useI18n} from "vue-i18n";

defineProps<{
  party: PartyWithAggregates
}>();

const { t } = useI18n();
</script>

<template>
  <div class="card card-translated party-card">
    <h2 class="party-card-name">{{ party.name }}</h2>
    <p>{{ dateFormat(party.createDate) }}</p>
    <p>{{ party.description }}</p>
    <div class="party-card-meta">
      <span>{{ party.participantsCount }} {{ t('labels.participants') }}</span>
      <span>{{ party.spendingsCount }} {{ t('labels.spendings') }}</span>
    </div>
    <div class="party-card-amount">
      {{ t('labels.total') }}: {{ currencyFormat(party.totalAmount) }}
    </div>
  </div>
</template>

<style scoped>

</style>