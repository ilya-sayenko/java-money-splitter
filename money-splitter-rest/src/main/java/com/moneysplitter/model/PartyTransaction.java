package com.moneysplitter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PartyTransaction {

    private UUID id;

    private UUID partyId;

    private PartyParticipant payer;

    private PartyParticipant payee;

    private BigDecimal amount;

    private TransactionStatus status;
}
