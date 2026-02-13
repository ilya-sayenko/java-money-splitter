<script setup lang="ts">
import {computed, reactive, ref} from "vue";
import {SignInRequest} from "@/http/auth/models/SignInRequest.ts";
import {useAuthStore} from "@/stores/authStore.ts";
import {useRouter} from "vue-router";
import {useI18n} from "vue-i18n";
import useVuelidate, {type ErrorObject} from "@vuelidate/core";
import {email, helpers, minLength, required} from "@vuelidate/validators";

const { t } = useI18n();
const errorText = ref('');
const authStore = useAuthStore();
const router = useRouter();

const state = reactive({
  email: '',
  password: ''
});

const rules = computed(() => ({
  email: {
    required: helpers.withMessage(t('errors.emailRequired'), required),
    email: helpers.withMessage(t('errors.emailIncorrect'), email)
  },
  password: {
    required: helpers.withMessage(t('errors.passwordRequired'), required),
    minLength: helpers.withMessage(t('Пароль должен быть минимум 6 символов'), minLength(6))
  }
}));

const v$ = useVuelidate(rules, state);

function routeToSignUpPage() {
  router.push({ name: 'SignUp' });
}

function errorMessage(errors: ErrorObject[]) {
  if (errors.length > 0) {
    return errors[0] ? errors[0].$message : '';
  }

  return '';
}

async function signIn() {
  const isFormValid = await v$.value.$validate();

  if (!isFormValid) {
    return;
  }

  const request = new SignInRequest();
  request.email = state.email;
  request.password = state.password;

  try {
    await authStore.signIn(request);
    await router.push({ name: 'Main'} );
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
          <input
            type="text"
            id="email"
            v-model="state.email"
            @input="v$.email.$reset"
          />
          <small class="error" v-if="v$.email.$error">{{ errorMessage(v$.email.$errors) }}</small>
        </div>

        <div class="form-group">
          <label for="password">{{ t('labels.password') }} *</label>
          <input
            type="password"
            id="password"
            v-model="state.password"
            @input="v$.password.$reset"
          />
          <small class="error" v-if="v$.password.$error">{{ errorMessage(v$.password.$errors) }}</small>
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