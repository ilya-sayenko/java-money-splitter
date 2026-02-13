<script setup lang="ts">
import {useRouter} from "vue-router";
import {useAuthStore} from "@/stores/authStore.ts";
import {storeToRefs} from "pinia";
import {ref} from "vue";
import {useI18n} from "vue-i18n";

const { t } = useI18n();
const router = useRouter();
const authStore = useAuthStore();
const { isLoggedIn, user } = storeToRefs(authStore);
const isShowUserPopup = ref(false);

function routeToMainPage() {
  router.push({ name: 'Main' });
}

function routeToSignInPage() {
  router.push({ name: 'SignIn' });
}

function routeToProfilePage() {
  hideUserPopup();
  router.push({ name: 'Profile' });
}

function routeToUserPartiesPage() {
  hideUserPopup();
  router.push({ name: 'ProfileParties' });
}

function routeToUserStatisticsPage() {
  hideUserPopup();
  router.push({ name: 'ProfileStatistics' });
}

function toggleUserPopup() {
  isShowUserPopup.value = !isShowUserPopup.value;
}

function hideUserPopup() {
  isShowUserPopup.value = false;
}

function signOut() {
  authStore.signOut();
  hideUserPopup();
  router.push('/');
}
</script>

<template>
    <header class="header-main">
      <div class="container">
        <div class="header-content">
          <h2 @click="routeToMainPage">💰 MoneySplitter</h2>
          <button class="btn btn-sign-in" @click="routeToSignInPage" v-if="!isLoggedIn">{{ t('buttons.signIn') }}</button>
          <div v-if="isLoggedIn" class="user-section">
            <div class="user-nav-item" @click="toggleUserPopup">
              <span>{{ user?.email }}</span>
              <span class="user-icon">👤</span>
            </div>
            <div class="user-popup" :class="{ 'active': isShowUserPopup }">
              <div class="popup-content">
                <div class="user-popup-name">{{ user?.email }}</div>
                <button class="btn btn-popup" @click="routeToProfilePage">{{ t('userPopup.profile') }}</button>
                <button class="btn btn-popup" @click="routeToUserPartiesPage">{{ t('userPopup.events') }}</button>
                <button class="btn btn-popup" @click="routeToUserStatisticsPage">{{ t('userPopup.statistics') }}</button>
                <button class="btn btn-popup" @click="signOut">{{ t('userPopup.signOut') }}</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </header>
</template>

<style lang="scss" scoped>
@use "@/assets/scss/colors.scss";

.header-main {
  background: colors.$background-dark;
  color: colors.$text-light;
  padding: 1rem;
  margin-bottom: 0.5rem;
}

.header-main h2 {
  text-align: center;
  cursor: pointer;
}

.header-main p {
  text-align: center;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0.5rem;
}

.btn-sign-in {
  color: colors.$background-dark;
  background: colors.$background-light;
  width: auto;
  text-align: center;
  padding: 0.5rem 1rem;
  white-space: nowrap;
}
</style>