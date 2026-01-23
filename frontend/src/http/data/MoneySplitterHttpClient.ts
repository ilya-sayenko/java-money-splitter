import type {Party} from "@/models/Party.ts";
import axios, {type AxiosResponse} from "axios";
import type {Participant} from "@/models/Participant.ts";
import type {Spending} from "@/models/Spending.ts";
import type {Transaction} from "@/models/Transaction.ts";
import type {PartyCreateRequest} from "@/http/data/models/PartyCreateRequest.ts";
import type {ParticipantCreateRequest} from "@/http/data/models/ParticipantCreateRequest.ts";
import type {SpendingCreateRequest} from "@/http/data/models/SpendingCreateRequest.ts";
import type {ParticipantUpdateRequest} from "@/http/data/models/ParticipantUpdateRequest.ts";

export class MoneySplitterHttpClient { // TODO singletone

  private baseUrl: string = import.meta.env.VITE_MONEY_SPLITTER_BACKEND_URL;

  async getPartyById(partyId: string): Promise<Party> {
    const response: AxiosResponse<Party> = await axios.get(`${this.baseUrl}/parties/${partyId}`);
    return response.data;
  }

  async getAllPartyById(partyIds: string[]): Promise<Party[]> {
    console.log(`${this.baseUrl}/parties?ids=${partyIds}`);
    const response: AxiosResponse<Party[]> = await axios.get(`${this.baseUrl}/parties?ids=${partyIds}`);
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

  async postParty(party: PartyCreateRequest): Promise<string> {
    const response: AxiosResponse<string> = await axios.post(`${this.baseUrl}/parties`, party);
    return response.data;
  }

  async postParticipant(participant: ParticipantCreateRequest): Promise<string> {
    const response: AxiosResponse<string> = await axios.post(`${this.baseUrl}/participants`, participant);
    return response.data;
  }

  async putParticipant(participant: ParticipantUpdateRequest): Promise<void> {
    return await axios.put(`${this.baseUrl}/participants`, participant);
  }

  async postSpending(spending: SpendingCreateRequest): Promise<string> {
    const response: AxiosResponse<string> = await axios.post(`${this.baseUrl}/spendings`, spending);
    return response.data;
  }

  async deleteParticipant(id: string): Promise<void> {
    return await axios.delete(`${this.baseUrl}/participants/${id}`);
  }

  async deleteSpending(id: string): Promise<void> {
    return await axios.delete(`${this.baseUrl}/spendings/${id}`);
  }
}