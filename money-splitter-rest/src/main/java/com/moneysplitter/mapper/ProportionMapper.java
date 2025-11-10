package com.moneysplitter.mapper;

import com.moneysplitter.config.MapperConfig;
import com.moneysplitter.controller.data.ProportionResponse;
import com.moneysplitter.controller.data.SpendingCreateRequest;
import com.moneysplitter.dao.postgresql.entity.ProportionEntity;
import com.moneysplitter.mapper.proportion.ProportionCalculatorFactory;
import com.moneysplitter.model.SpendingProportion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@Mapper(config = MapperConfig.class)
public abstract class ProportionMapper {

    @Autowired
    private ProportionCalculatorFactory proportionCalculatorFactory;

    @Autowired
    protected ParticipantMapper participantMapper;

    public List<SpendingProportion> fromSplitRequest(SpendingCreateRequest request) {
        return proportionCalculatorFactory.findCalculator(request.split().splitType()).calculate(request);
    }

    @Mapping(target = "participant", expression = "java(participantMapper.toResponse(proportion.getParticipant()))")
    public abstract ProportionResponse toResponse(SpendingProportion proportion);

    public abstract List<ProportionResponse> toResponses(List<SpendingProportion> proportions);

    public abstract ProportionEntity toEntity(SpendingProportion proportion);

    public abstract Set<ProportionEntity> toEntities(List<SpendingProportion> proportion);

    @Mapping(target = "participant", expression = "java(participantMapper.fromEntity(entity.getParticipant()))")
    public abstract SpendingProportion fromEntity(ProportionEntity entity);
}
