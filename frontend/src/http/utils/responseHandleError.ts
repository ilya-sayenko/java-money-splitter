import type {AxiosError} from "axios";
import Cookies from "js-cookie";
import {ACCESS_TOKEN_KEY} from "@/constants/cookie.ts";
import {refreshAccessToken} from "@/http/utils/refreshAccessToken.ts";

export async function responseHandleError(axiosError: AxiosError) {
  if (axiosError.response && [400, 401].includes(axiosError.response.status)) {
    try {
      const responseRefresh = await refreshAccessToken();
      if (responseRefresh) {
        Cookies.set(ACCESS_TOKEN_KEY, responseRefresh.access_token);
        return;
      }
    } catch(error) {
      Cookies.remove(ACCESS_TOKEN_KEY);
      window.location.href = '/';
      return;
    }
  }

  return Promise.reject(axiosError);
}