import type {AxiosError} from "axios";

export function requestHandleError(error: AxiosError) {
  return Promise.reject(error);
}