<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import {usePartyStore} from "@/stores/partyStore.ts";
import {storeToRefs} from "pinia";
import {useRoute} from "vue-router";
import {ParticipantCreateRequest} from "@/http/data/models/ParticipantCreateRequest.ts";
import ParticipantItem from "@/components/ParticipantItem.vue";
import {useI18n} from "vue-i18n";

const { t } = useI18n();
const participantName = ref('');
const route = useRoute();
const partyId = computed(() => route.params.partyId as string);
const partyStore = usePartyStore();
const { participants } = storeToRefs(partyStore);

const showParticipants = computed(() => {
  return participants.value && participants.value.length !== 0;
})

async function createParticipant() {
  const participant = new ParticipantCreateRequest();
  participant.partyId = partyId.value;
  participant.name = participantName.value;
  await partyStore.createParticipant(participant);
  await Promise.all([
    partyStore.loadParticipantsByPartyId(partyId.value),
    partyStore.loadTransactionsByPartyId(partyId.value)
  ])
  participantName.value = '';
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
      <input type="text" id="new-participant" v-model="participantName" />
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