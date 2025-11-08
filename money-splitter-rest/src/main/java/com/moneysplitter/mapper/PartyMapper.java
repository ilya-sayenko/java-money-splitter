package com.moneysplitter.mapper;

import com.moneysplitter.config.MapperConfig;
import com.moneysplitter.controller.data.PartyUpdateRequest;
import com.moneysplitter.dao.postgresql.entity.PartyEntity;
import com.moneysplitter.controller.data.PartyCreateRequest;
import com.moneysplitter.controller.data.PartyResponse;
import com.moneysplitter.model.Party;
import com.moneysplitter.model.PartyUpdateData;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class)
public interface PartyMapper {

    PartyResponse toResponse(Party party);

    PartyEntity toEntity(Party party);

    @Mapping(target = "totalAmount", ignore = true)
    Party fromEntity(PartyEntity partyEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalAmount", constant = "0")
    Party fromCreateRequest(PartyCreateRequest createRequest);

    PartyUpdateData fromUpdateRequest(PartyUpdateRequest updateRequest);

    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFields(PartyUpdateData participantUpdateData, @MappingTarget Party partyTarget);
}
