package com.moneysplitter.mapper;

import com.moneysplitter.config.MapperConfig;
import com.moneysplitter.dao.postgresql.entity.TransactionEntity;
import com.moneysplitter.controller.data.TransactionResponse;
import com.moneysplitter.model.PartyTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapperConfig.class)
public interface TransactionMapper {

    @Mapping(source = "payerId", target = "payer")
    @Mapping(source = "payeeId", target = "payee")
    TransactionResponse toResponse(PartyTransaction transaction);

    List<TransactionResponse> toResponses(List<PartyTransaction> transactions);

    PartyTransaction fromEntity(TransactionEntity entity); // TODO payer, payee

    List<PartyTransaction> fromEntity(List<TransactionEntity> entities);

    TransactionEntity toEntity(PartyTransaction transaction);

    List<TransactionEntity> toEntities(List<PartyTransaction> transactions);
}
