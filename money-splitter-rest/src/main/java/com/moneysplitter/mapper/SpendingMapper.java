package com.moneysplitter.mapper;

import com.moneysplitter.config.MapperConfig;
import com.moneysplitter.controller.data.SpendingCreateRequest;
import com.moneysplitter.dao.postgresql.entity.SpendingEntity;
import com.moneysplitter.controller.data.SpendingResponse;
import com.moneysplitter.model.PartySpending;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapperConfig.class, uses = ProportionMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SpendingMapper {

    @Mapping(target = "splitType", source = "split.splitType")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "proportions", expression = "java(proportionMapper.fromSplitRequest(request.split()))")
    PartySpending fromCreateRequest(SpendingCreateRequest request);

    @Mapping(target = "split", expression = "java(proportionMapper.toSplitResponse(spending))")
    @Mapping(target = "amounts", expression = "java(proportionMapper.toAmountsResponse(spending))")
    SpendingResponse toResponse(PartySpending spending);

    List<SpendingResponse> toResponses(List<PartySpending> spending);

    PartySpending fromEntity(SpendingEntity spendingEntity);

    List<PartySpending> fromEntities(List<SpendingEntity> spendingEntity);

    @Mapping(target = "proportions", expression = "java(proportionMapper.toEntities(spending.getProportions(), spending.getId()))")
    SpendingEntity toEntity(PartySpending spending);
}
