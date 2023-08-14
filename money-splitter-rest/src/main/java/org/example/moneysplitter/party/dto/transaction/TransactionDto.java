package org.example.moneysplitter.party.dto.transaction;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class TransactionDto {
    UUID id;
    UUID payer;
    UUID payee;
    BigDecimal amount;
    String status;
}
