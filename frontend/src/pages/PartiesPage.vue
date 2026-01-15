<script setup lang="ts">
import {usePartyStore} from "@/stores/partyStore.ts";
import {storeToRefs} from "pinia";
import PartyCard from "@/components/PartyCard.vue";
import {useRouter} from "vue-router";
import CreatePartyModal from "@/windows/CreatePartyModal.vue";
import {ref} from "vue";

const partyStore = usePartyStore();
const router = useRouter();
const { localParties } = storeToRefs(partyStore);
const isCreatePartyModalOpened = ref(false);

function routeToPartyPage(partyId: string) {
  router.push(`/parties/${partyId}`);
}

function openCreatePartyModal() {
  isCreatePartyModalOpened.value = true;
}

function closeCreatePartyModal() {
  isCreatePartyModalOpened.value = false;
}
</script>

<template>
  <div class="container">
    <div class="card">
      <h2>📅 Мои события</h2>
      <button class="btn btn-main" @click="openCreatePartyModal">Создать событие</button>

      <CreatePartyModal
        :isOpened="isCreatePartyModalOpened"
        @close="closeCreatePartyModal"
      ></CreatePartyModal>

      <div class="parties-grid">
        <PartyCard
          v-for="localParty in localParties"
          :id="localParty.id"
          :name="localParty.name"
          :description="localParty.description"
          @click="routeToPartyPage(localParty.id)"
        ></PartyCard>
      </div>
    </div>

  </div>

</template>

<style scoped>

</style>