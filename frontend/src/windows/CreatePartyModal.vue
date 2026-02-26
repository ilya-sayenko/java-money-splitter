<script setup lang="ts">
import {computed, reactive} from 'vue';
import {PartyCreateRequest} from "@/http/data/models/PartyCreateRequest.js";
import {usePartyStore} from "@/stores/partyStore.js";
import {useRouter} from "vue-router";
import {useAuthStore} from "@/stores/authStore.js";
import {storeToRefs} from "pinia";
import {useUserDataHttpClient} from "@/http/user/useUserDataHttpClient.js";
import {useI18n} from "vue-i18n";
import useVuelidate from "@vuelidate/core";
import {helpers, required} from "@vuelidate/validators";
import {errorMessage} from "@/utils/errorMessage.ts";

defineProps<{
  isOpened: boolean
}>();

const emits = defineEmits<{
  (e: 'close'): void
}>();

const closeModal = () => {
  emits('close');
};

const { t } = useI18n();
const partyStore = usePartyStore();
const router = useRouter();
const authStore = useAuthStore();
const { user } = storeToRefs(authStore);
const userDataHttpClient = useUserDataHttpClient();

const formState = reactive({
  partyName: '',
  partyDescription: ''
})

const rules = computed(() => ({
  partyName: {
    required: helpers.withMessage(t('errors.nameRequired'), required)
  },
  partyDescription: {}
}));

const v$ = useVuelidate(rules, formState);

async function createParty() {
  const isFormValid = await v$.value.$validate();

  if (!isFormValid) {
    return;
  }

  const party = new PartyCreateRequest();
  party.name = formState.partyName;
  party.description = formState.partyDescription;
  const newPartyId = await partyStore.createParty(party);

  if (user.value) {
    let userPartyIds = await userDataHttpClient.getPartyIdsByUserId(user.value.id);
    if (!userPartyIds) {
      userPartyIds = [];
    }
    userPartyIds.push(newPartyId);
    await userDataHttpClient.putPartyIds(user.value.id, userPartyIds);
  }

  await router.push(`/parties/${newPartyId}`);
}
</script>

<template>
  <Teleport to="body">
    <div v-if="isOpened" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <button class="modal-close-button" @click="closeModal">×</button>
        <h2>{{ t('headers.createParty') }}</h2>

        <div class="form-group">
          <label for="new-party-name">{{ t('labels.name') }}</label>
          <input type="text" id="new-party-name" v-model="formState.partyName" @input="v$.partyName.$reset()" />
          <small class="error" v-if="v$.partyName.$error">{{ errorMessage(v$.partyName.$errors) }}</small>
        </div>

        <div class="form-group">
          <label for="new-party-description">{{ t('labels.description') }}</label>
          <textarea id="new-party-description" v-model="formState.partyDescription" />
        </div>

        <button class="btn btn-main" @click="createParty">{{ t('buttons.createParty') }}</button>
      </div>
    </div>
  </Teleport>
</template>

<style scoped>
</style>
