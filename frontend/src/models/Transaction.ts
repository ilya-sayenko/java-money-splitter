import type {Participant} from "@/models/Participant.ts";

export class Transaction {

  id: string;

  payer: Participant;

  payee: Participant;

  amount: number;

  status: string;
}