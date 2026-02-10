<script setup>
import {useI18n} from "vue-i18n";

const { locale } = useI18n()

function updateLocale() {
  localStorage.setItem("locale", locale.value)
}
</script>

<template>
  <div class="language-switcher">
    <span class="language-switcher__earth material-symbols-outlined">language</span>
    <div class="language-switcher__wrapper">
      <select
        v-model="locale"
        @change="updateLocale"
        class="language-switcher__select"
      >
        <option value="ru">Русский</option>
        <option value="en">English</option>
      </select>
      <span class="language-switcher__icon material-symbols-outlined">
        expand_more
      </span>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@use "@/assets/scss/colors.scss";

.language-switcher {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 0.5rem;
  font-size: 0.9rem;
}

.language-switcher__wrapper {
  position: relative;
  display: inline-block;
}

.language-switcher__select {
  padding: 0.35rem 2.1rem 0.35rem 0.7rem;
  border-radius: 999px;
  border: 1px solid colors.$text-footer;
  background-color: rgba(0, 0, 0, 0.18);
  color: colors.$text-footer;
  font-size: 0.85rem;
  cursor: pointer;
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
}

.language-switcher__icon {
  position: absolute;
  top: 50%;
  right: 0.5rem;
  transform: translateY(-50%);
  font-size: 1.1rem;
  line-height: 1;
  pointer-events: none;
  color: colors.$text-footer;
}

.language-switcher__earth {
  font-size: 1.1rem;
}

.language-switcher__select:focus {
  outline: none;
  box-shadow: 0 0 0 2px rgba(255, 255, 255, 0.4);
}

.language-switcher__select option {
  color: #1a202c;
}
</style>