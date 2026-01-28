import Cookies from "js-cookie";
import {REFRESH_TOKEN_KEY} from "@/constants/cookie.ts";
import axios, {type AxiosResponse} from "axios";
import type {RefreshAccessTokenResponse} from "@/http/auth/models/RefreshAccessTokenResponse.ts";

const baseUrl = 'https://securetoken.googleapis.com/v1';
const apiKey = import.meta.env.VITE_FIREBASE_API_KEY;
const api = axios.create();

export async function refreshAccessToken() {
  debugger
  const refreshToken = Cookies.get(REFRESH_TOKEN_KEY);
  if (!refreshToken) {
    return;
  }
  const params = new URLSearchParams();
  params.append("grant_type", "refresh_token");
  params.append("refresh_token", refreshToken);
  const response: AxiosResponse<RefreshAccessTokenResponse> = await api.post(`${baseUrl}/token?key=${apiKey}`, params);

  return response.data;
}