import type {Participant} from "@/models/Participant.ts";
import type {TransactionStatus} from "@/models/TransactionStatus.ts";

export class Transaction {

  id: string;

  payer: Participant;

  payee: Participant;

  amount: number;

  status: TransactionStatus;
}