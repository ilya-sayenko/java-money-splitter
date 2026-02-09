<script setup lang="ts">
  import {storeToRefs} from "pinia";
  import {usePartyStore} from "@/stores/partyStore.ts";
  import {dateFormat} from "@/utils/dateFormat.ts";

  const partyStore = usePartyStore();
  const { party } = storeToRefs(partyStore);
</script>

<template>
  <div class="card party-header-card" v-if="party">
    <h2>{{ party.name }}</h2>
    <p>{{ dateFormat(party.createDate) }}</p>
    <p>{{ party.description }}</p>
  </div>
</template>

<style scoped>

</style>