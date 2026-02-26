import type {Participant} from "@/models/Participant.ts";
import type {Proportion} from "@/models/Proportion.ts";
import type {SplitType} from "@/models/SplitType.ts";

export class Spending {

  id: string;

  payer: Participant;

  name: string;

  amount: number;

  splitType: SplitType;

  proportions: Proportion[];
}