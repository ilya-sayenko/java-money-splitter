import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import type {Party} from "@/models/Party.ts";
import {MoneySplitterHttpClient} from "@/http/MoneySplitterHttpClient.ts";
import type {Participant} from "@/models/Participant.ts";
import type {Spending} from "@/models/Spending.ts";
import type {Transaction} from "@/models/Transaction.ts";

export const usePartyStore = defineStore('party', () => {
  const httpClient = new MoneySplitterHttpClient();
  const party = ref<Party>();
  const participants = ref<Participant[]>();
  const spendings = ref<Spending[]>();
  const transactions = ref<Transaction[]>();

  async function loadPartyById(partyId: string) {
    party.value = await httpClient.getPartyById(partyId);
  }

  async function loadParticipantsByPartyId(partyId: string) {
    participants.value = await httpClient.getParticipantsByPartyId(partyId);
  }

  async function loadSpendingsByPartyId(partyId: string) {
    spendings.value = await httpClient.getSpendingsByPartyId(partyId);
  }

  async function loadTransactionsByPartyId(partyId: string) {
    transactions.value = await httpClient.getTransactionsByPartyId(partyId);
  }

  return {
    party,
    participants,
    spendings,
    transactions,
    loadPartyById,
    loadParticipantsByPartyId,
    loadSpendingsByPartyId,
    loadTransactionsByPartyId
  };
})
