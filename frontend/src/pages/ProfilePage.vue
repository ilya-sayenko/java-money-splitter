<script setup lang="ts">
import {useAuthStore} from "@/stores/authStore.ts";
import {onMounted} from "vue";
import {storeToRefs} from "pinia";

const authStore = useAuthStore();
const { user } = storeToRefs(authStore);

onMounted(async () => {
  await authStore.loadProfile();
})
</script>

<template>

</template>

<style scoped>

</style>