package org.example.moneysplitter.rest.dto.transaction;

import lombok.Value;

@Value
public class UpdateTransactionStatusRequestDto {
    String status;
}
