<script setup lang="ts">
  const props = defineProps<{
    id: string,
    name: string,
    description: string
  }>();
</script>

<template>
  <div class="card card-translated party-header-card">
    <h2 class="party-card-name">{{ props.name }}</h2>
    <p>{{ props.description }}</p>
  </div>
</template>

<style scoped>

</style>