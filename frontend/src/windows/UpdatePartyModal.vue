<script setup lang="ts">
import {ref} from 'vue';
import {usePartyStore} from "@/stores/partyStore.js";
import {useI18n} from "vue-i18n";
import {PartyUpdateRequest} from "@/http/data/models/PartyUpdateRequest.ts";
import type {Party} from "@/models/Party.ts";

const props = defineProps<{
  isOpened: boolean,
  party: Party
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
}

const { t } = useI18n();
const partyName = ref(props.party.name);
const partyDescription = ref(props.party.description);
const partyStore = usePartyStore();

async function updateParty() {
  const party = new PartyUpdateRequest();
  party.id = props.party.id;
  party.name = partyName.value;
  party.description = partyDescription.value;
  await partyStore.updateParty(party);
  closeModal();
  reloadParties();
}
</script>

<template>
  <Teleport to="body">
    <div v-if="isOpened" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <button class="modal-close-button" @click="closeModal">×</button>
        <h2>{{ t('headers.updateParty') }}</h2>

        <div class="form-group">
          <label for="new-party-name">{{ t('labels.name') }}:</label>
          <input type="text" id="new-party-name" v-model="partyName" />
        </div>

        <div class="form-group">
          <label for="new-party-description">{{ t('labels.description') }}:</label>
          <textarea id="new-party-description" v-model="partyDescription" />
        </div>

        <button class="btn btn-main" @click="updateParty">{{ t('buttons.updateParty') }}</button>
      </div>
    </div>
  </Teleport>
</template>

<style scoped>
</style>
