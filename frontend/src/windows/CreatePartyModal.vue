<script setup lang="ts">
import {ref} from 'vue';
import {PartyCreateRequest} from "@/http/data/models/PartyCreateRequest.js";
import {usePartyStore} from "@/stores/partyStore.js";
import {useRouter} from "vue-router";
import {useAuthStore} from "@/stores/authStore.js";
import {storeToRefs} from "pinia";
import {useUserDataHttpClient} from "@/http/user/useUserDataHttpClient.js";

defineProps<{
  isOpened: boolean
}>();

const emits = defineEmits<{
  (e: 'close'): void
}>();

const closeModal = () => {
  emits('close');
};

const partyName = ref('');
const partyDescription = ref('');
const partyStore = usePartyStore();
const router = useRouter();
const authStore = useAuthStore();
const { user } = storeToRefs(authStore);
const userDataHttpClient = useUserDataHttpClient();

async function createParty() {
  const party = new PartyCreateRequest();
  party.name = partyName.value;
  party.description = partyDescription.value;
  const newPartyId = await partyStore.createParty(party);
  let userPartyIds = await userDataHttpClient.getPartyIdsByUserId(user.value!.id);
  if (!userPartyIds) {
    userPartyIds = [];
  }
  userPartyIds.push(newPartyId);
  await userDataHttpClient.putPartyIds(user.value!.id, userPartyIds);

  // partyStore.saveLocalParty({
  //   id: newPartyId,
  //   name: party.name,
  //   description: party.description
  // });
  await router.push(`/parties/${newPartyId}`);
}
</script>

<template>
  <div v-if="isOpened" class="modal-overlay" @click.self="closeModal">
    <div class="modal-content">
      <button class="modal-close-button" @click="closeModal">×</button>
      <h2>Создать событие</h2>

      <div class="form-group">
        <label for="new-party-name">Название</label>
        <input type="text" id="new-party-name" v-model="partyName" />
      </div>

      <div class="form-group">
        <label for="new-party-description">Описание</label>
        <textarea id="new-party-description" v-model="partyDescription" />
      </div>

      <button class="btn btn-main" @click="createParty">Создать событие</button>
    </div>
  </div>
</template>

<style scoped>
</style>
