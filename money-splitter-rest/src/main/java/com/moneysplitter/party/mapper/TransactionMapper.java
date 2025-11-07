package com.moneysplitter.party.mapper;

import com.moneysplitter.party.dao.postgresql.entity.TransactionEntity;
import com.moneysplitter.party.dto.transaction.TransactionDto;
import com.moneysplitter.party.model.PartyTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "payerId", target = "payer")
    @Mapping(source = "payeeId", target = "payee")
    TransactionDto toDTO(PartyTransaction party);

    List<TransactionDto> toDTOs(List<PartyTransaction> parties);

    PartyTransaction fromEntities(TransactionEntity entity);

    List<PartyTransaction> fromEntities(List<TransactionEntity> entities);

    List<TransactionEntity> toEntities(List<PartyTransaction> entities);
}
