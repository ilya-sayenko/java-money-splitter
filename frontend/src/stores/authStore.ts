import {defineStore} from "pinia";
import Cookies from 'js-cookie';
import {ref} from "vue";
import {AuthHttpClient} from "@/http/auth/AuthHttpClient.ts";
import type {LoginRequest} from "@/http/auth/models/LoginRequest.ts";
import {ProfileRequest} from "@/http/auth/models/ProfileRequest.ts";
import {UserMapper} from "@/mappers/UserMapper.ts";
import type {User} from "@/models/User.ts";
import {ACCESS_TOKEN_KEY, REFRESH_TOKEN_KEY} from "@/constants/cookie.ts";

export const useAuthStore = defineStore('authStore', () => {
  const isLoggedIn = ref(!!Cookies.get(ACCESS_TOKEN_KEY));
  const authHttpClient = new AuthHttpClient();
  const user = ref<User>();

  async function login(request: LoginRequest): Promise<void> {
    const response = await authHttpClient.login(request);
    Cookies.set(ACCESS_TOKEN_KEY, response.idToken);
    Cookies.set(REFRESH_TOKEN_KEY, response.refreshToken);
    isLoggedIn.value = true;

    const profileRequest = new ProfileRequest();
    profileRequest.idToken = response.idToken;
    await loadProfile();
  }

  function logout() {
    Cookies.remove(ACCESS_TOKEN_KEY);
    Cookies.remove(REFRESH_TOKEN_KEY);
    isLoggedIn.value = false;
  }

  async function loadProfile() {
    const request = new ProfileRequest();
    request.idToken = Cookies.get(ACCESS_TOKEN_KEY)!;
    const response = await authHttpClient.profile(request);
    user.value = UserMapper.fromProfileResponse(response);
  }

  return {
    isLoggedIn,
    login,
    logout,
    loadProfile,
    user
  };
});