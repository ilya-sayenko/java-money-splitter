<script setup lang="ts">
import PartyCard from "@/components/PartyCard.vue";
import {useRouter} from "vue-router";
import {storeToRefs} from "pinia";
import {onMounted, ref} from "vue";
import {useAuthStore} from "@/stores/authStore.ts";
import {useMoneySplitterHttpClient} from "@/http/data/useMoneySplitterHttpClient.ts";
import {useUserDataHttpClient} from "@/http/user/useUserDataHttpClient.ts";
import type {PartyWithAggregates} from "@/models/PartyWithAggregates.ts";
import {useI18n} from "vue-i18n";

const { t } = useI18n();
const router = useRouter();
const userParties = ref<PartyWithAggregates[]>();
const authStore = useAuthStore();
const { user } = storeToRefs(authStore);
const userDataHttpClient = useUserDataHttpClient();
const moneySplitterHttpClient = useMoneySplitterHttpClient();

function routeToPartyPage(partyId: string) {
  router.push(`/parties/${partyId}`);
}

async function loadUserParties() {
  const partyIds: string[] = await userDataHttpClient.getPartyIdsByUserId(user.value!.id);
  userParties.value = await moneySplitterHttpClient.getAllPartyWithAggregatesById(partyIds);
}

onMounted(async () => {
  if (user) {
    await loadUserParties();
  }
})
</script>

<template>
  <div class="container">
    <div class="card">
      <h2>📅 {{ t('headers.myParties') }}</h2>
      <div class="parties-grid">
        <PartyCard
          v-for="party in userParties"
          :party="party"
          @click="routeToPartyPage(party.id)"
        ></PartyCard>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>