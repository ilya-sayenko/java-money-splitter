package com.moneysplitter.mapper;

import com.moneysplitter.config.MapperConfig;
import com.moneysplitter.dao.postgresql.entity.TransactionEntity;
import com.moneysplitter.controller.data.TransactionResponse;
import com.moneysplitter.model.PartyTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(config = MapperConfig.class)
public abstract class TransactionMapper {

    @Autowired
    protected ParticipantMapper participantMapper;

    @Mapping(target = "payer", expression = "java(participantMapper.toResponse(transaction.getPayer()))")
    @Mapping(target = "payee", expression = "java(participantMapper.toResponse(transaction.getPayee()))")
    public abstract TransactionResponse toResponse(PartyTransaction transaction);

    public abstract List<TransactionResponse> toResponses(List<PartyTransaction> transactions);

    @Mapping(target = "payer", expression = "java(participantMapper.fromEntity(entity.getPayer()))")
    @Mapping(target = "payee", expression = "java(participantMapper.fromEntity(entity.getPayee()))")
    public abstract PartyTransaction fromEntity(TransactionEntity entity);

    public abstract List<PartyTransaction> fromEntities(List<TransactionEntity> entities);

    @Mapping(target = "payer", expression = "java(participantMapper.toEntity(transaction.getPayer()))")
    @Mapping(target = "payee", expression = "java(participantMapper.toEntity(transaction.getPayee()))")
    public abstract TransactionEntity toEntity(PartyTransaction transaction);

    public abstract List<TransactionEntity> toEntities(List<PartyTransaction> transactions);
}
