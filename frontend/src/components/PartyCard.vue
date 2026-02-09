<script setup lang="ts">
  import {dateFormat} from "@/utils/dateFormat.ts";
  import type {PartyWithAggregates} from "@/models/PartyWithAggregates.ts";
  import {currencyFormat} from "@/utils/currencyFormat.ts";

  defineProps<{
    party: PartyWithAggregates
  }>();
</script>

<template>
  <div class="card card-translated party-card">
    <h2 class="party-card-name">{{ party.name }}</h2>
    <p>{{ dateFormat(party.createDate) }}</p>
    <p>{{ party.description }}</p>
    <div class="party-card-meta">
      <span>{{ party.participantsCount }} участник(ов)</span>
      <span>{{ party.spendingsCount }} расход(ов)</span>
    </div>
    <div class="party-card-amount">
      Всего: {{ currencyFormat(party.totalAmount) }}
    </div>
  </div>
</template>

<style scoped>

</style>