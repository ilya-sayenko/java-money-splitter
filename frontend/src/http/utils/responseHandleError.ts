import type {AxiosError} from "axios";
import axios from "axios";
import Cookies from "js-cookie";
import {ACCESS_TOKEN_KEY} from "@/constants/cookie.ts";
import {refreshAccessToken} from "@/http/utils/refreshAccessToken.ts";
import {useRouter} from "vue-router";

const router = useRouter();

export async function responseHandleError(axiosError: AxiosError) {
  if (axiosError.response && [400, 401].includes(axiosError.response.status)) {
    try {
      const responseRefresh = await refreshAccessToken();
      if (responseRefresh) {
        Cookies.set(ACCESS_TOKEN_KEY, responseRefresh.access_token);
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