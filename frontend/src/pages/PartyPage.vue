<script setup lang="ts">
import PartyHeaderCard from "@/components/PartyHeaderCard.vue";
import ParticipantsCard from "@/components/ParticipantsCard.vue";
import SpendingsCard from "@/components/SpendingsCard.vue";
import TransactionsCard from "@/components/TransactionsCard.vue";
import {useRoute} from "vue-router";
import {computed, onMounted, ref} from "vue";
import {MoneySplitterHttpClient} from "@/http/MoneySplitterHttpClient.ts";
import type {Party} from "@/models/Party.ts";
import {usePartyStore} from "@/stores/partyStore.ts";
import {storeToRefs} from "pinia";

const route = useRoute();
const partyId = computed(() => route.params.partyId as string);
// const httpClient = new MoneySplitterHttpClient();
const partyStore = usePartyStore();
const { party } = storeToRefs(partyStore);

// const party = ref<Party>();



onMounted(async () => {
  await partyStore.loadPartyById(partyId.value)
  // party.value = await httpClient.getPartyById(partyId.value);

})

</script>

<template>
  <div class="container">
    <div class="party-page-content">

      <PartyHeaderCard v-if="party"
        :name="party.name"
        :description="party.description"
      ></PartyHeaderCard>

      <div class="party-page-details">
        <ParticipantsCard></ParticipantsCard>
        <SpendingsCard></SpendingsCard>
        <TransactionsCard></TransactionsCard>
      </div>

    </div>
  </div>
</template>

<style scoped>

</style>