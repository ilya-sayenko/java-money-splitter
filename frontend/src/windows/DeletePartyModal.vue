<script setup lang="ts">
import {useAuthStore} from "@/stores/authStore.js";
import {storeToRefs} from "pinia";
import {useUserDataHttpClient} from "@/http/user/useUserDataHttpClient.js";
import {useI18n} from "vue-i18n";

const props = defineProps<{
  isOpened: boolean,
  partyId: string
}>();

const emits = defineEmits<{
  (e: 'close'): void,
  (e: 'reloadParties'): void,
}>();

const closeModal = () => {
  emits('close');
};

const reloadParties = () => {
  emits('reloadParties');
};

const { t } = useI18n();
const authStore = useAuthStore();
const { user } = storeToRefs(authStore);
const userDataHttpClient = useUserDataHttpClient();

async function deleteParty() {
  const userPartyIds = await userDataHttpClient.getPartyIdsByUserId(user.value!.id);
  if (!userPartyIds) {
    return;
  }
  const filteredPartyIds = userPartyIds.filter(id => id !== props.partyId);
  await userDataHttpClient.putPartyIds(user.value!.id, filteredPartyIds);
  closeModal();
  reloadParties();
}
</script>

<template>
  <Teleport to="body">
    <div v-if="isOpened" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <button class="modal-close-button" @click="closeModal">×</button>
        <h2>{{ t('headers.deleteParty') }}</h2>

        <p class="description">{{ t('questions.deleteParty') }}</p>

        <div class="buttons-container">
          <button class="btn btn-main" @click="deleteParty">{{ t('buttons.yes') }}</button>
          <button class="btn btn-main" @click="closeModal">{{ t('buttons.no') }}</button>
        </div>
      </div>
    </div>
  </Teleport>
</template>

<style lang="scss" scoped>
.buttons-container {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
}

.description {
  font-size: 1rem;
  margin-bottom: 1rem;
  margin-top: 1rem;
  text-align: center;
}
</style>
