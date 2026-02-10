<script setup lang="ts">
import {ref} from "vue";
import {SignInRequest} from "@/http/auth/models/SignInRequest.ts";
import {useAuthStore} from "@/stores/authStore.ts";
import {useRouter} from "vue-router";
import {useI18n} from "vue-i18n";

const { t } = useI18n();
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
      <h2>{{ t('headers.authorization') }}</h2>
      <form @submit.prevent="signIn">
        <div class="form-group">
          <label for="email">{{ t('labels.email') }} *</label>
          <input type="text" id="email" v-model="email">
        </div>
        <div class="form-group">
          <label for="password">{{ t('labels.password') }} *</label>
          <input type="password" id="password" v-model="password">
        </div>
        <p class="error" v-if="errorText">{{ errorText }}</p>
        <div class="form-group">
          <button class="btn btn-main" type="submit">{{ t('buttons.signIn') }}</button>
        </div>
        <button class="btn btn-secondary" type="button" @click="routeToSignUpPage">{{ t('buttons.signUp') }}</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
</style>