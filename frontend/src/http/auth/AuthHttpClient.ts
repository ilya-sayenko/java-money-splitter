import axios, {type AxiosError, type AxiosResponse, type InternalAxiosRequestConfig} from "axios";
import type {LoginRequest} from "@/http/auth/models/LoginRequest.ts";
import type {LoginResponse} from "@/http/auth/models/LoginResponse.ts";
import type {ProfileRequest} from "@/http/auth/models/ProfileRequest.ts";
import type {ProfileResponse} from "@/http/auth/models/ProfileResponse.ts";
import Cookies from "js-cookie";
import {ACCESS_TOKEN_KEY, REFRESH_TOKEN_KEY} from "@/constants/cookie.ts";
import type {RefreshAccessTokenResponse} from "@/http/auth/models/RefreshAccessTokenResponse.ts";

export class AuthHttpClient { // TODO singletone

  private baseUrl: string = import.meta.env.VITE_FIREBASE_URL;

  private apiKey: string = import.meta.env.VITE_FIREBASE_API_KEY;

  private api = axios.create();

  constructor() {
    this.api.interceptors.request.use(
      this.requestHandleSuccess,
      this.requestHandleError
    );
    this.api.interceptors.response.use(
      this.responseHandleSuccess,
      this.responseHandleError
    )
  }

  protected requestHandleSuccess(config: InternalAxiosRequestConfig) {
    return config;
  }

  protected requestHandleError(error: AxiosError) {
    return Promise.reject(error);
  }

  protected responseHandleSuccess(response: AxiosResponse) {
    return response;
  }

  protected async responseHandleError(axiosError: AxiosError) {
    if (axiosError.response && [400, 401].includes(axiosError.response.status)) {
      try {
        const responseRefresh = await this.refreshAccessToken();
        if (responseRefresh) {
          Cookies.set(ACCESS_TOKEN_KEY, responseRefresh.access_token);
          return;
        }
      } catch(error) {
        Cookies.remove(ACCESS_TOKEN_KEY);
        window.location.href = '/auth';
        return;
      }
    }
  }

  async login(request: LoginRequest): Promise<LoginResponse> {
    const response: AxiosResponse<LoginResponse> = await axios.post(
      `${this.baseUrl}/accounts:signInWithPassword?key=${this.apiKey}`,
      request
    );
    return response.data;
  }

  async refreshAccessToken() {
    const refreshToken = Cookies.get(REFRESH_TOKEN_KEY);
    if (!refreshToken) {
      return;
    }
    const params = new URLSearchParams();
    params.append("grant_type", "refresh_token");
    params.append("refresh_token", refreshToken);
    const response: AxiosResponse<RefreshAccessTokenResponse> = await axios.post(`${this.baseUrl}/accounts:lookup?key=${this.apiKey}`);

    return response.data;
  }

  async profile(request: ProfileRequest): Promise<ProfileResponse> {
    const response: AxiosResponse<ProfileResponse> = await this.api.post(
      `${this.baseUrl}/accounts:lookup?key=${this.apiKey}`,
      request
    );
    return response.data;
  }
}