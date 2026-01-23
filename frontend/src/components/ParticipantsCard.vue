<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import {usePartyStore} from "@/stores/partyStore.ts";
import {storeToRefs} from "pinia";
import {useRoute} from "vue-router";
import {ParticipantCreateRequest} from "@/http/data/models/ParticipantCreateRequest.ts";
import ParticipantItem from "@/components/ParticipantItem.vue";

const route = useRoute();

const partyId = computed(() => route.params.partyId as string);

const partyStore = usePartyStore();

const { participants } = storeToRefs(partyStore);

const showParticipants = computed(() => {
  return participants.value && participants.value.length !== 0;
})

const participantName = ref('');

const isEditParticipant = ref(false);

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

async function deleteParticipantById(participantId: string) {
  await partyStore.deleteParticipantById(participantId);
  await Promise.all([
      partyStore.loadSpendingsByPartyId(partyId.value),
      partyStore.loadTransactionsByPartyId(partyId.value),
      partyStore.loadParticipantsByPartyId(partyId.value)
  ]);
}

onMounted(async () => {
  await partyStore.loadParticipantsByPartyId(partyId.value);
});
</script>

<template>
  <div class="card card-translated participants-card">
    <h2>👥 Участники</h2>
    <div class="form-group">
      <label for="new-participant">Имя участника</label>
      <input type="text" id="new-participant" placeholder="Андрей" v-model="participantName" />
    </div>
    <button class="btn btn-main" @click="createParticipant">Добавить участника</button>

    <div v-if="showParticipants">
      <h3 class="participants-list-title">Список участников:</h3>
      <ul class="participants-list">
        <ParticipantItem v-for="participant in participants" :participant="participant"></ParticipantItem>
<!--        <li class="participant-item" v-for="participant in participants" :key="participant.id">-->
<!--          <span class="participant-name" v-if="!isEditParticipant">{{ participant.name }}</span>-->
<!--          <input class="participant-item-new-name" type="text" v-if="isEditParticipant" @blur="isEditParticipant = false"/>-->
<!--          <div>-->
<!--            <button class="btn-edit" @click="isEditParticipant = true">✏️</button>-->
<!--            <button @click="deleteParticipantById(participant.id)">❌</button>-->
<!--          </div>-->
<!--        </li>-->
      </ul>
    </div>
  </div>
</template>

<style scoped>

</style>