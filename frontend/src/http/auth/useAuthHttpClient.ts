import {AuthHttpClient} from "@/http/auth/AuthHttpClient.ts";

const authHttpClient = new AuthHttpClient();

export function useAuthHttpClient() {
  return authHttpClient;
}