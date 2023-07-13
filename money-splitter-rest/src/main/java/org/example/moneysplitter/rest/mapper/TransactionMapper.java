package org.example.moneysplitter.rest.mapper;

import lombok.experimental.UtilityClass;
import org.example.moneysplitter.rest.dao.postgresql.entity.TransactionEntity;
import org.example.moneysplitter.rest.dto.transaction.TransactionDto;
import org.example.moneysplitter.rest.model.PartyTransaction;

@UtilityClass
public class TransactionMapper {
    public TransactionDto toDto(PartyTransaction party) {
        return TransactionDto
                .builder()
                .id(party.getId())
                .payer(party.getPayerId())
                .payee(party.getPayeeId())
                .amount(party.getAmount())
                .status(party.getStatus().name())
                .build();
    }

    public PartyTransaction fromEntity(TransactionEntity entity) {
        return PartyTransaction
                .builder()
                .id(entity.getId())
                .partyId(entity.getParty().getId())
                .payerId(entity.getPayer().getId())
                .payeeId(entity.getPayee().getId())
                .amount(entity.getAmount())
                .status(PartyTransaction.Status.valueOf(entity.getStatus().name()))
                .build();
    }
}
