import {defineStore} from "pinia";
import Cookies from 'js-cookie';
import {ref} from "vue";
import type {SignInRequest} from "@/http/auth/models/SignInRequest.ts";
import {ProfileRequest} from "@/http/auth/models/ProfileRequest.ts";
import {UserMapper} from "@/mappers/UserMapper.ts";
import type {User} from "@/models/User.ts";
import {ACCESS_TOKEN_KEY, REFRESH_TOKEN_KEY} from "@/constants/cookie.ts";
import {useAuthHttpClient} from "@/http/auth/useAuthHttpClient.ts";
import type {SignUpRequest} from "@/http/auth/models/SignUpRequest.ts";

export const useAuthStore = defineStore('authStore', () => {
  const isLoggedIn = ref(!!Cookies.get(ACCESS_TOKEN_KEY));
  const authHttpClient = useAuthHttpClient();
  const user = ref<User>();

  async function signIn(request: SignInRequest): Promise<void> {
    const response = await authHttpClient.signIn(request);
    Cookies.set(ACCESS_TOKEN_KEY, response.idToken);
    Cookies.set(REFRESH_TOKEN_KEY, response.refreshToken);
    isLoggedIn.value = true;

    const profileRequest = new ProfileRequest();
    profileRequest.idToken = response.idToken;
    await loadProfile();
  }

  function signOut() {
    Cookies.remove(ACCESS_TOKEN_KEY);
    Cookies.remove(REFRESH_TOKEN_KEY);
    isLoggedIn.value = false;
  }

  async function signUp(request: SignUpRequest): Promise<void> {
   await authHttpClient.signUp(request);
  }

  async function loadProfile() {
    const request = new ProfileRequest();
    request.idToken = Cookies.get(ACCESS_TOKEN_KEY)!;
    const response = await authHttpClient.profile(request);
    user.value = UserMapper.fromProfileResponse(response);
  }

  return {
    isLoggedIn,
    signIn,
    signOut,
    signUp,
    loadProfile,
    user
  };
});