package org.example.moneysplitter.rest.mapper;

import org.example.moneysplitter.rest.dao.postgresql.entity.SpendingEntity;
import org.example.moneysplitter.rest.dto.spending.SpendingDto;
import org.example.moneysplitter.rest.model.PartySpending;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProportionMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SpendingMapper {
    @Mapping(target = "splitType", expression = "java(PartySpending.SplitType.valueOf(spendingDto.getSplit().getSplitType().toUpperCase()))")
    @Mapping(target = "proportions", expression = "java(proportionMapper.fromSplitDto(spendingDto.getSplit()))")
    PartySpending fromDTO(SpendingDto spendingDto);

    @Mapping(target = "split", expression = "java(proportionMapper.toSplitDto(spending))")
    @Mapping(target = "amounts", expression = "java(proportionMapper.toAmountsDto(spending))")
    SpendingDto toDTO(PartySpending spending);

    List<SpendingDto> toDTOs(List<PartySpending> spending);

    PartySpending fromEntity(SpendingEntity spendingEntity);

    List<PartySpending> fromEntities(List<SpendingEntity> spendingEntity);

    @Mapping(target = "proportions", expression = "java(proportionMapper.toEntities(spending.getProportions(), spending.getId()))")
    SpendingEntity toEntity(PartySpending spending);
}
