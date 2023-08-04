package org.example.moneysplitter.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class PartyTransaction {
    private UUID id;
    private UUID partyId;
    private UUID payerId;
    private UUID payeeId;
    private BigDecimal amount;
    private Status status;

    public enum Status {
        PENDING, CLOSED
    }
}
