<script setup lang="ts">
import {useRouter} from "vue-router";
import {useAuthStore} from "@/stores/authStore.ts";
import {storeToRefs} from "pinia";
import {ref} from "vue";

const router = useRouter();
const authStore = useAuthStore();
const { isLoggedIn, user } = storeToRefs(authStore);
const isShowUserPopup = ref(false);

function routeToMainPage() {
  router.push('/');
}

function routeToAuthPage() {
  router.push('/auth');
}

function routeToUserPage() {
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

function showUserPopup() {
  isShowUserPopup.value = true;
}

function hideUserPopup() {
  isShowUserPopup.value = false;
}

function logout() {
  authStore.logout();
  hideUserPopup();
  router.push('/');
}
</script>

<template>
    <header class="header-main">
      <div class="container">
        <div class="header-content">
          <h2 @click="routeToMainPage">💰 MoneySplitter</h2>
          <button class="btn btn-sign-in" @click="routeToAuthPage" v-if="!isLoggedIn">Войти</button>
          <div v-if="isLoggedIn" class="user-section">
            <div class="user-nav-item" @click="toggleUserPopup">
              <span>{{ user?.email }}</span>
              <span class="user-icon">👤</span>
            </div>
            <div class="user-popup" :class="{ 'active': isShowUserPopup }">
              <div class="popup-content">
                <div class="user-popup-name">{{ user?.email }}</div>
                <button class="btn btn-popup" @click="routeToUserPage">Профиль</button>
                <button class="btn btn-popup" @click="routeToUserPartiesPage">События</button>
                <button class="btn btn-popup" @click="routeToUserStatisticsPage">Статистика</button>
                <button class="btn btn-popup" @click="logout">Выйти</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </header>
</template>

<style scoped>
</style>