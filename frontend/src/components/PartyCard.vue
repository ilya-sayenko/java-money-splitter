<script setup lang="ts">
  const props = defineProps<{
    id: string,
    name: string,
    description: string
  }>();

  // TODO add data
</script>

<template>
  <div class="card card-translated party-card">
    <h2 class="party-card-name">{{ props.name }}</h2>
    <p>26.01.2026</p>
    <p>{{ props.description }}</p>
    <div class="party-card-meta">
      <span>0 участник(ов)</span>
      <span>0 расход(ов)</span>
    </div>
    <div class="party-card-amount">
      Всего: {{ 500 }}
    </div>
  </div>
</template>

<style scoped>

</style>