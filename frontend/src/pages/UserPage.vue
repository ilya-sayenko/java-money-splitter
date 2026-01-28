<script setup lang="ts">
import {useAuthStore} from "@/stores/authStore.ts";
import {storeToRefs} from "pinia";

const authStore = useAuthStore();
const { user } = storeToRefs(authStore);
</script>

<template>
  <div class="container">
    <div class="card">
      <h2>{{ user?.name }}</h2>
    </div>
  </div>
</template>

<style scoped>

</style>