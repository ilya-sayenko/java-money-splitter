<script setup lang="ts">
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";

</script>

<template>
  <div class="wrapper">
    <div class="content">
      <Header></Header>
      <RouterView></RouterView>
    </div>
    <Footer></Footer>
  </div>
</template>

<style scoped></style>
