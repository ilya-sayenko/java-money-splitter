<script setup lang="ts">
  const props = defineProps<{
    name: string,
    description?: string
  }>()
</script>

<template>
  <div class="card party-header-card">
    <h2>{{ props.name }}</h2>
    <p>Дата создания: {{ props.description }}</p>
    <p>{{ props.description }}</p>
  </div>
</template>

<style scoped>

</style>