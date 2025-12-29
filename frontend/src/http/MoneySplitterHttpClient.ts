import type {Party} from "@/models/Party.ts";
import axios, {type AxiosResponse} from "axios";
import type {Participant} from "@/models/Participant.ts";
import type {Spending} from "@/models/Spending.ts";
import type {Transaction} from "@/models/Transaction.ts";

export class MoneySplitterHttpClient {

  private baseUrl: string = 'http://localhost:8080/api/v1'; // TODO env variable

  async getPartyById(partyId: string): Promise<Party> {
    const response: AxiosResponse<Party> = await axios.get(`${this.baseUrl}/parties/${partyId}`);
    return response.data;
  }

  async getParticipantsByPartyId(partyId: string): Promise<Participant[]> {
    const response: AxiosResponse<Participant[]> = await axios.get(`${this.baseUrl}/parties/${partyId}/participants`);
    return response.data;
  }

  async getSpendingsByPartyId(partyId: string): Promise<Spending[]> {
    const response: AxiosResponse<Spending[]> = await axios.get(`${this.baseUrl}/parties/${partyId}/spendings`);
    return response.data;
  }

  async getTransactionsByPartyId(partyId: string): Promise<Transaction[]> {
    const response: AxiosResponse<Transaction[]> = await axios.get(`${this.baseUrl}/parties/${partyId}/transactions`);
    return response.data;
  }
}