import type {TransactionStatus} from "@/models/TransactionStatus.ts";

export class TransactionUpdateRequest {

  id: string;

  status: TransactionStatus;
}