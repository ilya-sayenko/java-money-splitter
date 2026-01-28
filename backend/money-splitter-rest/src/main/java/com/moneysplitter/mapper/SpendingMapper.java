package com.moneysplitter.mapper;

import com.moneysplitter.config.MapperConfig;
import com.moneysplitter.controller.data.SpendingCreateRequest;
import com.moneysplitter.dao.postgresql.entity.SpendingEntity;
import com.moneysplitter.controller.data.SpendingResponse;
import com.moneysplitter.model.PartySpending;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(config = MapperConfig.class)
public abstract class SpendingMapper {

    protected ParticipantMapper participantMapper;

    protected ProportionMapper proportionMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "splitType", source = "split.splitType")
    @Mapping(target = "payer", expression = "java(PartyParticipant.builder().id(request.payerId()).build())")
    @Mapping(target = "proportions", expression = "java(proportionMapper.fromSplitRequest(request))")
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    public abstract PartySpending fromCreateRequest(SpendingCreateRequest request);

    @Mapping(target = "payer", expression = "java(participantMapper.toResponse(spending.getPayer()))")
    @Mapping(target = "proportions", expression = "java(proportionMapper.toResponses(spending.getProportions()))")
    public abstract SpendingResponse toResponse(PartySpending spending);

    public abstract List<SpendingResponse> toResponses(List<PartySpending> spending);

    @Mapping(target = "payer", expression = "java(participantMapper.fromEntity(spendingEntity.getPayer()))")
    @Mapping(target = "proportions", expression = "java(proportionMapper.fromEntities(spendingEntity.getProportions()))")
    public abstract PartySpending fromEntity(SpendingEntity spendingEntity);

    public abstract List<PartySpending> fromEntities(List<SpendingEntity> spendingEntities);

    @Mapping(target = "proportions", expression = "java(proportionMapper.toEntities(spending.getProportions()))")
    public abstract SpendingEntity toEntity(PartySpending spending);

    @Autowired
    public void setParticipantMapper(ParticipantMapper participantMapper) {
        this.participantMapper = participantMapper;
    }

    @Autowired
    public void setProportionMapper(ProportionMapper proportionMapper) {
        this.proportionMapper = proportionMapper;
    }
}
