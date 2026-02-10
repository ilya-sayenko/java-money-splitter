<script setup lang="ts">
import {ref} from "vue";
import {useAuthStore} from "@/stores/authStore.ts";
import {useRouter} from "vue-router";
import {SignUpRequest} from "@/http/auth/models/SignUpRequest.ts";
import type {ErrorResponse} from "@/http/auth/models/ErrorResponse.ts";
import {useI18n} from "vue-i18n";

const { t } = useI18n();
const name = ref('')
const email = ref('');
const password = ref('');
const errorText = ref('');
const authStore = useAuthStore();
const router = useRouter();

async function signUp() {
  const request = new SignUpRequest();
  request.name = name.value;
  request.email = email.value;
  request.password = password.value;
  try {
    await authStore.signUp(request);
    await router.push("/");
  } catch(error) {
    const errorResponse: ErrorResponse = error.response.data;
    errorText.value = errorResponse.error.message; // TODO get message by code
  }
}
</script>

<template>
  <div class="container">
    <div class="card auth-form">
      <h2>Регистрация</h2>
      <form @submit.prevent="signUp">
        <div class="form-group">
          <label for="name">Имя *</label>
          <input type="text" id="name" v-model="name">
        </div>
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
          <button class="btn btn-main" type="submit">{{ t('buttons.signUp') }}</button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
</style>