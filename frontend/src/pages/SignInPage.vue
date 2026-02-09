<script setup lang="ts">
import {ref} from "vue";
import {SignInRequest} from "@/http/auth/models/SignInRequest.ts";
import {useAuthStore} from "@/stores/authStore.ts";
import {useRouter} from "vue-router";

const email = ref('');
const password = ref('');
const errorText = ref('');
const authStore = useAuthStore();
const router = useRouter();

function routeToSignUpPage() {
  router.push("/signUp");
}

async function signIn() {
  const request = new SignInRequest();
  request.email = email.value;
  request.password = password.value;
  try {
    await authStore.signIn(request);
    await router.push("/");
  } catch(error) {
    errorText.value = 'Неверный email или пароль';
  }
}
</script>

<template>
  <div class="container">
    <div class="card auth-form">
      <h2>Авторизация</h2>
      <form @submit.prevent="signIn">
        <div class="form-group">
          <label for="email">Email *</label>
          <input type="text" id="email" v-model="email">
        </div>
        <div class="form-group">
          <label for="password">Пароль *</label>
          <input type="password" id="password" v-model="password">
        </div>
        <p class="error" v-if="errorText">{{ errorText }}</p>
        <div class="form-group">
          <button class="btn btn-main" type="submit">Войти</button>
        </div>
        <button class="btn btn-secondary" type="button" @click="routeToSignUpPage">Регистрация</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
</style>