<script setup lang="ts">
import Header from "@/components/Header.vue";
import PartyPage from "@/pages/PartyPage.vue";
import Footer from "@/components/Footer.vue";
</script>

<template>
  <div class="wrapper">
    <Header></Header>
    <RouterView></RouterView>
    <Footer></Footer>
  </div>
</template>

<style scoped></style>
