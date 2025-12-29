<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import {usePartyStore} from "@/stores/partyStore.ts";
import {storeToRefs} from "pinia";
import {useRoute} from "vue-router";

const route = useRoute();
const partyId = computed(() => route.params.partyId as string);
const partyStore = usePartyStore();
const { participants } = storeToRefs(partyStore);

onMounted(async () => {
  await partyStore.loadParticipantsByPartyId(partyId.value);
  console.log(participants);
})
</script>

<template>
  <div class="card participants-card">
    <h2>👥 Участники</h2>
    <div class="form-group">
      <label for="new-participant">Имя участника</label>
      <input type="text" id="new-participant" placeholder="Андрей" />
    </div>
    <button class="btn btn-main">Добавить участника</button>

    <h3 class="participants-list-title">Список участников:</h3>
    <ul class="participants-list">
      <li class="participant-item" v-for="participant in participants" :key="participant.id">
        <span class="participant-name">{{ participant.name }}</span>
        <div>
          <button class="btn-edit">✏️</button>
          <button>❌</button>
        </div>
      </li>
    </ul>
  </div>
</template>

<style scoped>

</style>