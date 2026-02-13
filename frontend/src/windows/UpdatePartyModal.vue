<script setup lang="ts">
import {computed, reactive} from 'vue';
import {usePartyStore} from "@/stores/partyStore.js";
import {useI18n} from "vue-i18n";
import {PartyUpdateRequest} from "@/http/data/models/PartyUpdateRequest.ts";
import type {Party} from "@/models/Party.ts";
import {helpers, required} from "@vuelidate/validators";
import useVuelidate from "@vuelidate/core";
import {errorMessage} from "@/utils/errorMessage.ts";

const props = defineProps<{
  isOpened: boolean,
  party: Party
}>();

const emits = defineEmits<{
  (e: 'close'): void,
  (e: 'reloadParties'): void,
}>();

const { t } = useI18n();
const partyStore = usePartyStore();

const formState = reactive({
  partyName: props.party.name,
  partyDescription: props.party.description
})

const rules = computed(() => ({
  partyName: {
    required: helpers.withMessage(t('errors.nameRequired'), required)
  },
  partyDescription: {}
}));

const v$ = useVuelidate(rules, formState);

const closeModal = () => {
  emits('close');
  formState.partyName = props.party.name;
  formState.partyDescription = props.party.description;
};

const reloadParties = () => {
  emits('reloadParties');
}

async function updateParty() {
  const isFormValid = await v$.value.$validate();

  if (!isFormValid) {
    return;
  }

  const party = new PartyUpdateRequest();
  party.id = props.party.id;
  party.name = formState.partyName;
  party.description = formState.partyDescription;
  await partyStore.updateParty(party);
  closeModal();
  reloadParties();

  v$.value.$reset();
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
          <input type="text" id="new-party-name" v-model="formState.partyName" @input="v$.partyName.$reset()" />
          <small class="error" v-if="v$.partyName.$error">{{ errorMessage(v$.partyName.$errors) }}</small>
        </div>

        <div class="form-group">
          <label for="new-party-description">{{ t('labels.description') }}:</label>
          <textarea id="new-party-description" v-model="formState.partyDescription" />
        </div>

        <button class="btn btn-main" @click="updateParty">{{ t('buttons.updateParty') }}</button>
      </div>
    </div>
  </Teleport>
</template>

<style scoped>
</style>
