import type {Participant} from "@/models/Participant.ts";

export class Spending {

  id: string;

  payer: Participant;

  name: string;

  amount: number;
}