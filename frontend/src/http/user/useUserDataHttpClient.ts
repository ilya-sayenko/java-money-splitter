import {UserDataHttpClient} from "@/http/user/UserDataHttpClient.ts";

const userDataHttpClient = new UserDataHttpClient();

export function useUserDataHttpClient() {
  return userDataHttpClient;
}