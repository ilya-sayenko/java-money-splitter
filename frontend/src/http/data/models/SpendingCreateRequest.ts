import type {SplitRequest} from "@/http/data/models/SplitRequest.ts";

export class SpendingCreateRequest {

  partyId: string;

  payerId: string;

  name: string;

  amount!: number;

  split: SplitRequest;
}