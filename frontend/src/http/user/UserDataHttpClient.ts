import axios, {type AxiosError, type AxiosResponse, type InternalAxiosRequestConfig} from "axios";
import Cookies from "js-cookie";
import {ACCESS_TOKEN_KEY, REFRESH_TOKEN_KEY} from "@/constants/cookie.ts";
import type {RefreshAccessTokenResponse} from "@/http/auth/models/RefreshAccessTokenResponse.ts";

export class UserDataHttpClient { // TODO singletone

  private baseUrl: string = import.meta.env.VITE_FIREBASE_DB_URL;

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

  async getPartyIdsByUserId(userId: string): Promise<string[]> {
    const response: AxiosResponse<string[]> = await this.api.get(`${this.baseUrl}/userParties/${userId}.json?auth=${Cookies.get(ACCESS_TOKEN_KEY)}`);
    return response.data;
  }

  async putPartyIds(userId: string, partyIds: string[]): Promise<void> {
    return await this.api.put(`${this.baseUrl}/userParties/${userId}.json?auth=${Cookies.get(ACCESS_TOKEN_KEY)}`, partyIds);
  }
}