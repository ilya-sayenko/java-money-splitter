<script setup lang="ts">
import {useRouter} from "vue-router";

const router = useRouter();

function routeToMainPage() {
  router.push("/");
}
</script>

<template>
    <header class="header-main">
      <h1 @click="routeToMainPage">💰 MoneySplitter</h1>
      <p>Лёгкий способ разделить совместные затраты между друзьями</p>
    </header>
</template>

<style scoped>

</style>