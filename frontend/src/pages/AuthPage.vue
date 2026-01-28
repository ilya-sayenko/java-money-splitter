<script setup lang="ts">
import {ref} from "vue";
import {LoginRequest} from "@/http/auth/models/LoginRequest.ts";
import {useAuthStore} from "@/stores/authStore.ts";
import {useRouter} from "vue-router";

const email = ref('');
const password = ref('');
const errorText = ref('');
const authStore = useAuthStore();
const router = useRouter();

async function authenticate() {
  const request = new LoginRequest();
  request.email = email.value;
  request.password = password.value;
  try {
    await authStore.login(request);
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
      <form @submit.prevent="authenticate">
        <div class="form-group">
          <label for="email">Email *</label>
          <input type="text" id="email" v-model="email">
        </div>
        <div class="form-group">
          <label for="password">Пароль *</label>
          <input type="password" id="password" v-model="password">
        </div>
        <p class="error" v-if="errorText">{{ errorText }}</p>
        <button class="btn btn-main" type="submit">Войти</button>
      </form>
    </div>
  </div>
</template>

<style scoped>

</style>