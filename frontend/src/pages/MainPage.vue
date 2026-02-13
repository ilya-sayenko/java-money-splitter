<script setup lang="ts">
import CreatePartyModal from "@/windows/CreatePartyModal.vue";
import {ref} from "vue";
import {useI18n} from "vue-i18n";

const { t } = useI18n();

const isShowCreatePartyModal = ref(false);

function showCreatePartyModal() {
  isShowCreatePartyModal.value = true;
}

function onCloseCreatePartyModal() {
  isShowCreatePartyModal.value = false;
}
</script>

<template>
  <div class="container">
    <div class="main-page">
      <section class="hero">
        <div class="hero-text">
          <h1 class="hero-title">
            {{ t('mainPage.title') }}
            <span class="accent">{{ t('mainPage.titleAccent') }}</span>
          </h1>
          <p class="hero-subtitle">{{ t('mainPage.heroSubtitle') }}</p>

          <div class="hero-actions">
            <button
              class="btn btn-main btn-create-party"
              @click="showCreatePartyModal"
            >
              {{ t("buttons.createParty") }}
            </button>

            <p class="hero-note">{{ t('mainPage.heroNote') }}</p>
          </div>
        </div>

        <div class="hero-card">
          <div class="hero-card-row">
            <span class="dot dot--green"></span>
            <span>{{ t('mainPage.heroCardRow1') }}</span>
          </div>
          <div class="hero-card-row">
            <span class="dot dot--violet"></span>
            <span>{{ t('mainPage.heroCardRow2') }}</span>
          </div>
          <div class="hero-card-row">
            <span class="dot dot--blue"></span>
            <span>{{ t('mainPage.heroCardRow3') }}</span>
          </div>
        </div>
      </section>

      <section class="features">
        <div class="feature">
          <h3>{{ t('mainPage.featureTitle1') }}</h3>
          <p>{{ t('mainPage.featureDescription1') }}</p>
        </div>
        <div class="feature">
          <h3>{{ t('mainPage.featureTitle2') }}</h3>
          <p>{{ t('mainPage.featureDescription2') }}</p>
        </div>
        <div class="feature">
          <h3>{{ t('mainPage.featureTitle3') }}</h3>
          <p>{{ t('mainPage.featureDescription3') }}</p>
        </div>
      </section>
    </div>
  </div>

  <CreatePartyModal
    :isOpened="isShowCreatePartyModal"
    @close="onCloseCreatePartyModal"
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