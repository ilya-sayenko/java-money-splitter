<script setup lang="ts">
import PartyCard from "@/components/PartyCard.vue";
import {useRouter} from "vue-router";
import {storeToRefs} from "pinia";
import {onMounted, ref} from "vue";
import {useAuthStore} from "@/stores/authStore.ts";
import type {Party} from "@/models/Party.ts";
import {UserDataHttpClient} from "@/http/user/UserDataHttpClient.ts";
import {MoneySplitterHttpClient} from "@/http/data/MoneySplitterHttpClient.ts";

const router = useRouter();
const userParties = ref<Party[]>();
const authStore = useAuthStore();
const { isLoggedIn, user } = storeToRefs(authStore);
const userDataHttpClient = new UserDataHttpClient();
const moneySplitterHttpClient = new MoneySplitterHttpClient();

function routeToPartyPage(partyId: string) {
  router.push(`/parties/${partyId}`);
}

onMounted(async () => {
  if (isLoggedIn && user.value) {
    const partyIds: string[] = await userDataHttpClient.getPartyIdsByUserId(user.value.id);
    userParties.value = await moneySplitterHttpClient.getAllPartyById(partyIds);
  }
  console.log(userParties.value);
})
</script>

<template>
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
<!--      <PartyCard-->
<!--        v-for="localParty in localParties"-->
<!--        :id="localParty.id"-->
<!--        :name="localParty.name"-->
<!--        :description="localParty.description"-->
<!--        @click="routeToPartyPage(localParty.id)"-->
<!--      ></PartyCard>-->
    </div>
  </div>
</template>

<style scoped>

</style>