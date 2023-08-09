package org.example.moneysplitter.rest.mapper;

import org.example.moneysplitter.rest.dao.postgresql.entity.TransactionEntity;
import org.example.moneysplitter.rest.dto.transaction.TransactionDto;
import org.example.moneysplitter.rest.model.PartyTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(source = "payerId", target = "payer")
    @Mapping(source = "payeeId", target = "payee")
    TransactionDto toDto(PartyTransaction party);

    @Mapping(source = "payerId", target = "payer")
    @Mapping(source = "payeeId", target = "payee")
    List<TransactionDto> toDto(List<PartyTransaction> parties);

//    @Mapping(source = "status", )
    PartyTransaction fromEntity(TransactionEntity entity);

    List<PartyTransaction> fromEntity(List<TransactionEntity> entities);

    List<TransactionEntity> toEntity(List<PartyTransaction> entities);
}
