import axios, {type AxiosResponse} from "axios";
import type {LoginRequest} from "@/http/auth/models/LoginRequest.ts";
import type {LoginResponse} from "@/http/auth/models/LoginResponse.ts";
import type {ProfileRequest} from "@/http/auth/models/ProfileRequest.ts";
import type {ProfileResponse} from "@/http/auth/models/ProfileResponse.ts";
import {requestHandleSuccess} from "@/http/utils/requestHandleSuccess.ts";
import {requestHandleError} from "@/http/utils/requestHandleError.ts";
import {responseHandleSuccess} from "@/http/utils/responseHandleSuccess.ts";
import {responseHandleError} from "@/http/utils/responseHandleError.ts";

export class AuthHttpClient { // TODO singletone

  private baseUrl: string = import.meta.env.VITE_FIREBASE_URL;

  private apiKey: string = import.meta.env.VITE_FIREBASE_API_KEY;

  private api = axios.create();

  constructor() {
    this.api.interceptors.request.use(
      requestHandleSuccess,
      requestHandleError
    );
    this.api.interceptors.response.use(
      responseHandleSuccess,
      responseHandleError
    )
  }

  async login(request: LoginRequest): Promise<LoginResponse> {
    const response: AxiosResponse<LoginResponse> = await this.api.post(
      `${this.baseUrl}/accounts:signInWithPassword?key=${this.apiKey}`,
      request
    );
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