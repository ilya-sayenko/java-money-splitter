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
  router.push('/');
}

function routeToSignInPage() {
  router.push('/signIn');
}

function routeToProfilePage() {
  hideUserPopup();
  router.push('/profile');
}

function routeToUserPartiesPage() {
  hideUserPopup();
  router.push('/profile/parties');
}

function routeToUserStatisticsPage() {
  hideUserPopup();
  router.push('/profile/statistics');
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

<style scoped>
</style>