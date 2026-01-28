<script setup lang="ts">
import PartyCard from "@/components/PartyCard.vue";
import {useRouter} from "vue-router";
import {storeToRefs} from "pinia";
import {onMounted, ref} from "vue";
import {useAuthStore} from "@/stores/authStore.ts";
import type {Party} from "@/models/Party.ts";
import {useMoneySplitterHttpClient} from "@/http/data/useMoneySplitterHttpClient.ts";
import {useUserDataHttpClient} from "@/http/user/useUserDataHttpClient.ts";

const router = useRouter();
const userParties = ref<Party[]>();
const authStore = useAuthStore();
const { user } = storeToRefs(authStore);
const userDataHttpClient = useUserDataHttpClient(); //new UserDataHttpClient();
const moneySplitterHttpClient = useMoneySplitterHttpClient();// new MoneySplitterHttpClient();

function routeToPartyPage(partyId: string) {
  router.push(`/parties/${partyId}`);
}

async function loadUserParties() {
  const partyIds: string[] = await userDataHttpClient.getPartyIdsByUserId(user.value!.id);
  userParties.value = await moneySplitterHttpClient.getAllPartyById(partyIds);
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
      <h2>📅 Мои события</h2>
      <div class="parties-grid">
        <PartyCard
          v-for="party in userParties"
          :id="party.id"
          :name="party.name"
          :description="party.description"
          @click="routeToPartyPage(party.id)"
        ></PartyCard>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>