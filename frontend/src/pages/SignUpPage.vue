<script setup lang="ts">
import {computed, reactive, ref} from "vue";
import {useAuthStore} from "@/stores/authStore.ts";
import {useRouter} from "vue-router";
import {SignUpRequest} from "@/http/auth/models/SignUpRequest.ts";
import type {ErrorResponse} from "@/http/auth/models/ErrorResponse.ts";
import {useI18n} from "vue-i18n";
import {email, helpers, minLength, required} from "@vuelidate/validators";
import useVuelidate from "@vuelidate/core";
import {errorMessage} from "@/utils/errorMessage.ts";

const { t } = useI18n();
const errorText = ref('');
const authStore = useAuthStore();
const router = useRouter();

const formState = reactive({
  name: '',
  email: '',
  password: ''
})

const rules = computed(() => ({
  name: {
    requires: helpers.withMessage(t('errors.nameRequired'), required)
  },
  email: {
    required: helpers.withMessage(t('errors.emailRequired'), required),
    email: helpers.withMessage(t('errors.emailIncorrect'), email)
  },
  password: {
    required: helpers.withMessage(t('errors.passwordRequired'), required),
    minLength: helpers.withMessage(t('errors.passwordIncorrect'), minLength(6))
  }
}));

const v$ = useVuelidate(rules, formState);

async function signUp() {
  const isFormValid = await v$.value.$validate();

  if (!isFormValid) {
    return;
  }

  const request = new SignUpRequest();
  request.displayName = formState.name;
  request.email = formState.email;
  request.password = formState.password;
  try {
    await authStore.signUp(request);
    await router.push({ name: 'Main' });
  } catch(error) {
    const errorResponse: ErrorResponse = error.response.data;
    errorText.value = getMessage(errorResponse.error.message);
  }
}

function getMessage(messageCode: string) {
  if (messageCode === "EMAIL_EXISTS") {
    return t('errors.emailExists');
  } else {
    return t('errors.registrationError');
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
          <input type="text" id="name" v-model="formState.name">
          <small class="error" v-if="v$.name.$error">{{ errorMessage(v$.name.$errors) }}</small>
        </div>
        <div class="form-group">
          <label for="email">Email *</label>
          <input type="text" id="email" v-model="formState.email" @input="v$.email.$reset()">
          <small class="error" v-if="v$.email.$error">{{ errorMessage(v$.email.$errors) }}</small>
        </div>
        <div class="form-group">
          <label for="password">Пароль *</label>
          <input type="password" id="password" v-model="formState.password" @input="v$.password.$reset()">
          <small class="error" v-if="v$.password.$error">{{ errorMessage(v$.password.$errors) }}</small>
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