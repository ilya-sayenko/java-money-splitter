<script setup lang="ts">
import CreatePartyModal from "@/windows/CreatePartyModal.vue";
import {ref} from "vue";
import {useI18n} from "vue-i18n";

const { t } = useI18n();

const isShowCreatePartyModal = ref(false);

function showCreatePartyModal() {
  isShowCreatePartyModal.value = true;
}

function hideCreatePartyModal() {
  isShowCreatePartyModal.value = false;
}
</script>

<template>
  <div class="container">
    <div class="main-page">
      <section class="hero">
        <div class="hero-text">
          <h1 class="hero-title">
            Легкий способ разделить
            <span class="accent">совместные расходы</span>
          </h1>
          <p class="hero-subtitle">
            Планируете поездку, вечеринку или квартирные траты?
            Создайте событие, фиксируйте покупки и сервис посчитает,
            кто кому и сколько должен.
          </p>

          <div class="hero-actions">
            <button
              class="btn btn-main btn-create-party"
              @click="showCreatePartyModal"
            >
              {{ t("buttons.createParty") }}
            </button>
            <p class="hero-note">
              Не нужно таблиц и сложных формул — только участники и их траты.
            </p>
          </div>
        </div>

        <div class="hero-card">
          <div class="hero-card-row">
            <span class="dot dot--green"></span>
            <span>Вы отмечаете, кто платит и за что</span>
          </div>
          <div class="hero-card-row">
            <span class="dot dot--violet"></span>
            <span>Сервис делит сумму поровну или по долям</span>
          </div>
          <div class="hero-card-row">
            <span class="dot dot--blue"></span>
            <span>В итоге — понятный список, кто кому должен</span>
          </div>
        </div>
      </section>

      <section class="features">
        <div class="feature">
          <h3>События и участники</h3>
          <p>
            Создавайте отдельные события под поездки, вечеринки, ремонты и любые
            общие затраты. Добавляйте участников и следите за их балансом.
          </p>
        </div>
        <div class="feature">
          <h3>Удобный учет расходов</h3>
          <p>
            Фиксируйте покупки в пару кликов: кто заплатил, сумму и тип разделения —
            поровну или по индивидуальным долям.
          </p>
        </div>
        <div class="feature">
          <h3>Прозрачные расчёты</h3>
          <p>
            Сервис считает, кто в итоге должен и кому. Вам остаётся только
            договориться о переводах, без споров и «кто платил за что».
          </p>
        </div>
      </section>
    </div>
  </div>

  <CreatePartyModal
    :isOpened="isShowCreatePartyModal"
    @close="hideCreatePartyModal"
  ></CreatePartyModal>
</template>

<style lang="scss" scoped>
@use "@/assets/scss/_colors.scss";

.main-page {
  padding: 1.5rem 0;
  display: flex;
  flex-direction: column;
  gap: 2rem;
  margin: 0.5rem
}

.hero {
  display: grid;
  grid-template-columns: minmax(0, 2.1fr) minmax(0, 1.4fr);
  gap: 1.5rem;
  align-items: center;
}

.hero-title {
  margin: 0 0 0.8rem;
  font-size: 2rem;
  line-height: 1.15;
}

.accent {
  display: inline-block;
  position: relative;
}

.hero-subtitle {
  margin: 0 0 1rem;
  font-size: 0.9rem;
  max-width: 520px;
}

.hero-actions {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  align-items: flex-start;
}

.hero-note {
  margin: 0;
  font-size: 0.9rem;
}

.hero-card {
  border-radius: 12px;
  padding: 1rem 1rem;
  border: 1px solid rgba(15, 23, 42, 0.10);
  background: radial-gradient(circle at top left, #eef2ff 0, #f9fafb 45%, #e0f2fe 100%);
  display: flex;
  flex-direction: column;
  gap: 0.6rem;
}

.hero-card-row {
  display: flex;
  align-items: center;
  gap: 0.6rem;
  font-size: 0.9rem;
}

.dot {
  width: 10px;
  height: 10px;
  border-radius: 10px;
}

.dot--green {
  background: #22c55e;
}

.dot--violet {
  background: #6366f1;
}

.dot--blue {
  background: #0ea5e9;
}

.features {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 1rem;
}

.feature {
  padding: 1rem 1rem;
  border-radius: 1.1rem;
  border: 1px solid rgba(15, 23, 42, 0.08);
  background: colors.$white;
}

.feature h3 {
  margin: 0 0 0.5rem;
  font-size: 1rem;
}

.feature p {
  margin: 0;
  font-size: 0.8rem;
  color: colors.$text-midtone;
}

@media (max-width: 900px) {
  .hero {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 720px) {
  .hero-title {
    font-size: 1.5rem;
  }
  .features {
    grid-template-columns: 1fr;
  }
}
</style>