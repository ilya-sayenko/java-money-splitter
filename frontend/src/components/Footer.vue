<script setup lang="ts">
import LanguageSwitcher from "@/components/LanguageSwitcher.vue";

const currentYear = new Date().getFullYear();
</script>

<template>
  <footer class="footer-main">
    <div class="container">
        <p>© {{ currentYear }} MoneySplitter</p>
        <LanguageSwitcher></LanguageSwitcher>
    </div>
  </footer>
</template>

<style scoped>
</style>