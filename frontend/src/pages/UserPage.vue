<script setup lang="ts">
import {useAuthStore} from "@/stores/authStore.ts";
import {storeToRefs} from "pinia";
import {computed} from "vue";
import {useRouter} from "vue-router";
import {useI18n} from "vue-i18n";

const { t } = useI18n();
const authStore = useAuthStore();
const { user } = storeToRefs(authStore);

const router = useRouter();

const initials = computed(() => {
  const name = user.value?.name?.trim();
  if (!name) return 'U';
  const parts = name.split(/\s+/).filter(Boolean);
  const a = parts[0]?.[0] ?? 'U';
  const b = parts.length > 1 ? (parts[parts.length - 1]?.[0] ?? '') : '';
  return (a + b).toUpperCase();
});

const displayName = computed(() => user.value?.name?.trim() || 'Пользователь');
const displayEmail = computed(() => user.value?.email?.trim() || '—');
const displayId = computed(() => user.value?.id?.trim() || '—');

async function reloadProfile() {
  await authStore.loadProfile();
}

function signOut() {
  authStore.signOut();
  router.push({ name: 'Main' });
}

function routeToParties() {
  router.push({ name: 'ProfileParties' });
}

function routeToStatistics() {
  router.push({ name: 'ProfileStatistics' });
}
</script>

<template>
  <div class="container user-page">
    <div class="card user-card">
      <div class="user-card__header">
        <div class="avatar" aria-hidden="true">{{ initials }}</div>
        <div class="user-card__title">
          <h2 class="user-name">{{ displayName }}</h2>
          <div class="user-subtitle">{{ displayEmail }}</div>
        </div>

        <div class="user-card__actions">
          <button class="btn btn-main" @click="reloadProfile()">{{ t('buttons.update') }}</button>
          <button class="btn btn-main" @click="signOut()">{{ t('buttons.signOut') }}</button>
        </div>
      </div>

      <div class="user-card__body">
        <div class="info-grid">
          <div class="info-item">
            <div class="info-label">Email</div>
            <div class="info-value">{{ displayEmail }}</div>
          </div>
          <div class="info-item">
            <div class="info-label">User ID</div>
            <div class="info-value mono">{{ displayId }}</div>
          </div>
        </div>

        <div class="quick-links">
          <button class="quick-link" @click="routeToParties">
            <div class="quick-link__title">{{ t('userPage.events') }}</div>
            <div class="quick-link__desc">{{ t('userPage.eventsDescription') }}</div>
          </button>
          <button class="quick-link" @click="routeToStatistics">
            <div class="quick-link__title">{{ t('userPage.statistics') }}</div>
            <div class="quick-link__desc">{{ t('userPage.statisticsDescription') }}</div>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@use "@/assets/scss/_colors.scss";

.user-page {
  padding: 1rem 0;
}

.user-card {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.user-card__header {
  display: grid;
  grid-template-columns: auto 1fr auto;
  gap: 0.9rem;
  align-items: center;
}

.avatar {
  width: 56px;
  height: 56px;
  border-radius: 16px;
  display: grid;
  place-items: center;
  font-weight: 800;
  color: #0f172a;
  background: linear-gradient(135deg, #e2e8f0, #c7d2fe);
  border: 1px solid rgba(15, 23, 42, 0.08);
}

.user-name {
  margin: 0;
  line-height: 1.2;
}

.user-subtitle {
  color: colors.$text-midtone;
  font-size: 0.9rem;
  word-break: break-word;
}

.user-card__actions {
  display: flex;
  gap: 0.7rem;
  flex-wrap: wrap;
  justify-content: flex-end;
}

.user-card__body {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 0.8rem;
}

.info-item {
  padding: 0.8rem;
  border-radius: 12px;
  border: 1px solid rgba(15, 23, 42, 0.08);
  background: rgba(255, 255, 255, 0.6);
}

.info-label {
  font-size: 0.8rem;
  color: rgba(15, 23, 42, 0.6);
  margin-bottom: 0.5rem;
}

.info-value {
  font-size: 1rem;
  color: rgba(15, 23, 42, 0.92);
  word-break: break-word;
}

.quick-links {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 0.9rem;
}

.quick-link {
  text-align: left;
  padding: 0.9rem;
  border-radius: 12px;
  border: 1px solid rgba(15, 23, 42, 0.08);
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.85), rgba(255, 255, 255, 0.55));
  cursor: pointer;
  transition: transform 120ms ease, box-shadow 120ms ease, border-color 120ms ease;
}

.quick-link:hover {
  transform: translateY(-1px);
  box-shadow: 0 8px 24px rgba(15, 23, 42, 0.08);
  border-color: rgba(99, 102, 241, 0.28);
}

.quick-link__title {
  font-weight: 700;
  color: rgba(15, 23, 42, 0.92);
}

.quick-link__desc {
  margin-top: 0.5rem;
  font-size: 0.8rem;
  color: rgba(15, 23, 42, 0.65);
}

@media (max-width: 720px) {
  .user-card__header {
    grid-template-columns: auto 1fr;
  }
  .user-card__actions {
    grid-column: 1 / -1;
    justify-content: flex-start;
  }
  .info-grid,
  .quick-links {
    grid-template-columns: 1fr;
  }
}
</style>