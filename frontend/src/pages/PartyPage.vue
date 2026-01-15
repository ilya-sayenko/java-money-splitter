<script setup lang="ts">
import PartyHeaderCard from "@/components/PartyHeaderCard.vue";
import ParticipantsCard from "@/components/ParticipantsCard.vue";
import SpendingsCard from "@/components/SpendingsCard.vue";
import TransactionsCard from "@/components/TransactionsCard.vue";
import {useRoute} from "vue-router";
import {computed, onMounted} from "vue";
import {usePartyStore} from "@/stores/partyStore.ts";

const route = useRoute();
const partyId = computed(() => route.params.partyId as string);
const partyStore = usePartyStore();

onMounted(async () => {
  await partyStore.loadPartyById(partyId.value);
})

</script>

<template>
  <div class="container">
    <div class="party-page-content">
      <PartyHeaderCard></PartyHeaderCard>
      <div class="party-page-details">
        <ParticipantsCard></ParticipantsCard>
        <SpendingsCard></SpendingsCard>
      </div>
      <div class="party-page-details">
        <TransactionsCard></TransactionsCard>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>