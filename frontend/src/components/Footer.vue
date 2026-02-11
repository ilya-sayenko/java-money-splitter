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

<style lang="scss" scoped>
@use "@/assets/scss/colors.scss";

.footer-main {
  background: colors.$background-footer;
  padding: 1.5rem;
  color: colors.$text-footer;
  width: 100%;
  font-size: 0.9rem;
}

.footer-main p {
  text-align: center;
}
</style>