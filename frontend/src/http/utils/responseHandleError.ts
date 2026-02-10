import type {AxiosError} from "axios";
import axios from "axios";
import Cookies from "js-cookie";
import {ACCESS_TOKEN_KEY} from "@/constants/cookie.ts";
import {refreshAccessToken} from "@/http/utils/refreshAccessToken.ts";
import router from "@/router";

export async function responseHandleError(axiosError: AxiosError) {
  if (axiosError.response && [400, 401].includes(axiosError.response.status)) {
    try {
      const responseRefresh = await refreshAccessToken();
      debugger
      if (responseRefresh) {
        Cookies.set(ACCESS_TOKEN_KEY, responseRefresh.id_token);
      }
      if (axiosError.config) {
        return axios(axiosError.config);
      }
    } catch(error) {
      Cookies.remove(ACCESS_TOKEN_KEY);
      await router.push('/');
      return;
    }
  }

  return Promise.reject(axiosError);
}