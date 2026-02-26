import {MoneySplitterHttpClient} from "@/http/data/MoneySplitterHttpClient.ts";

const moneySplitterHttpClient = new MoneySplitterHttpClient();

export function useMoneySplitterHttpClient() {
  return moneySplitterHttpClient;
}