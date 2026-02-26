import axios, {type AxiosResponse} from "axios";
import Cookies from "js-cookie";
import {ACCESS_TOKEN_KEY} from "@/constants/cookie.ts";
import {requestHandleSuccess} from "@/http/utils/requestHandleSuccess.ts";
import {requestHandleError} from "@/http/utils/requestHandleError.ts";
import {responseHandleSuccess} from "@/http/utils/responseHandleSuccess.ts";
import {responseHandleError} from "@/http/utils/responseHandleError.ts";

export class UserDataHttpClient {

  private baseUrl: string = import.meta.env.VITE_FIREBASE_DB_URL;

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

  async getPartyIdsByUserId(userId: string): Promise<string[]> {
    const response: AxiosResponse<string[]> = await this.api.get(`${this.baseUrl}/userParties/${userId}.json?auth=${Cookies.get(ACCESS_TOKEN_KEY)}`);
    return response.data;
  }

  async putPartyIds(userId: string, partyIds: string[]): Promise<void> {
    return await this.api.put(`${this.baseUrl}/userParties/${userId}.json?auth=${Cookies.get(ACCESS_TOKEN_KEY)}`, partyIds);
  }
}