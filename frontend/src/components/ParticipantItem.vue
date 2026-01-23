<script setup lang="ts">
import type {Participant} from "@/models/Participant.ts";
import {computed, nextTick, ref, useTemplateRef} from "vue";
import {usePartyStore} from "@/stores/partyStore.ts";
import {useRoute} from "vue-router";
import {ParticipantUpdateRequest} from "@/http/data/models/ParticipantUpdateRequest.ts";

const props = defineProps<{
  participant: Participant;
}>();

const isEditParticipant = ref(false);

const participantNewName = ref('');

const participantNewNameInput = useTemplateRef("participantNewNameInput");

const route = useRoute();

const partyId = computed(() => route.params.partyId as string);

const partyStore = usePartyStore();

async function deleteParticipantById(participantId: string) {
  await partyStore.deleteParticipantById(participantId);
  await Promise.all([
    partyStore.loadSpendingsByPartyId(partyId.value),
    partyStore.loadTransactionsByPartyId(partyId.value),
    partyStore.loadParticipantsByPartyId(partyId.value)
  ]);
}

function editParticipant() {
  isEditParticipant.value = true;
  participantNewName.value = props.participant.name;
  nextTick(() => {
    if (participantNewNameInput.value) {
      participantNewNameInput.value.focus();
    }
  });
}

async function saveParticipant() {
  const request = new ParticipantUpdateRequest();
  request.id = props.participant.id;
  request.name = participantNewName.value;
  await partyStore.updateParticipant(request);
  isEditParticipant.value = false;
  await Promise.all([
    partyStore.loadParticipantsByPartyId(partyId.value),
    partyStore.loadSpendingsByPartyId(partyId.value),
    partyStore.loadTransactionsByPartyId(partyId.value)
  ]);
}
</script>

<template>
  <li class="participant-item">
    <span class="participant-name" v-show="!isEditParticipant">{{ participant.name }}</span>
    <input
        class="participant-item-new-name"
        type="text"
        ref="participantNewNameInput"
        v-show="isEditParticipant"
        @blur="saveParticipant()"
        @keyup.enter="saveParticipant()"
        v-model="participantNewName"
    />
    <div>
      <button class="btn-edit" @click="editParticipant">✏️</button>
      <button @click="deleteParticipantById(participant.id)">❌</button>
    </div>
  </li>
</template>

<style scoped>

</style>