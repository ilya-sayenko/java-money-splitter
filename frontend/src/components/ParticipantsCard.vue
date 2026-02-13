<script setup lang="ts">
import {computed, onMounted, reactive} from "vue";
import {usePartyStore} from "@/stores/partyStore.ts";
import {storeToRefs} from "pinia";
import {useRoute} from "vue-router";
import {ParticipantCreateRequest} from "@/http/data/models/ParticipantCreateRequest.ts";
import ParticipantItem from "@/components/ParticipantItem.vue";
import {useI18n} from "vue-i18n";
import {helpers, required} from "@vuelidate/validators";
import useVuelidate from "@vuelidate/core";
import {errorMessage} from "@/utils/errorMessage.ts";

const { t } = useI18n();
const route = useRoute();
const partyId = computed(() => route.params.partyId as string);
const partyStore = usePartyStore();
const { participants } = storeToRefs(partyStore);

const formState = reactive({
  participantName: ''
})

const rules = computed(() => ({
  participantName: {
    required: helpers.withMessage(t('errors.participantNameRequired'), required),
    unique: helpers.withMessage(
      t('errors.participantNameUnique'),
      (value: string) => !participants.value || !participants.value.map(p => p.name).includes(value)
    )
  }
}))

const v$ = useVuelidate(rules, formState);

const showParticipants = computed(() => {
  return participants.value && participants.value.length !== 0;
})

async function createParticipant() {
  const isFormValid = await v$.value.$validate();

  if (!isFormValid) {
    return;
  }

  const participant = new ParticipantCreateRequest();
  participant.partyId = partyId.value;
  participant.name = formState.participantName;
  await partyStore.createParticipant(participant);
  await Promise.all([
    partyStore.loadParticipantsByPartyId(partyId.value),
    partyStore.loadTransactionsByPartyId(partyId.value)
  ])

  formState.participantName = '';
  v$.value.$reset();
}

onMounted(async () => {
  await partyStore.loadParticipantsByPartyId(partyId.value);
});
</script>

<template>
  <div class="card card-translated participants-card">
    <h2>👥 {{ t('headers.participants') }}</h2>
    <div class="form-group">
      <label for="new-participant">{{ t('labels.participantName') }}:</label>
      <input type="text" id="new-participant" v-model="formState.participantName" @input="v$.participantName.$reset()" />
      <small class="error" v-if="v$.participantName.$error">{{ errorMessage(v$.participantName.$errors) }}</small>
    </div>
    <button class="btn btn-main" @click="createParticipant">{{ t('buttons.addParticipant') }}</button>

    <div v-if="showParticipants">
      <h3 class="participants-list-title">{{ t('participantsCard.list') }}:</h3>
      <ul class="participants-list">
        <ParticipantItem v-for="participant in participants" :participant="participant"></ParticipantItem>
      </ul>
    </div>
  </div>
</template>

<style scoped>

</style>