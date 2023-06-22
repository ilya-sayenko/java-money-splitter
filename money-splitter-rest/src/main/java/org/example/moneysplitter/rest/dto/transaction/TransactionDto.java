package org.example.moneysplitter.rest.dto.transaction;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class TransactionDto {
    String id;
    String payer;
    String payee;
    BigDecimal amount;
    String status;
}
