package com.moneysplitter.mapper;

import com.moneysplitter.config.MapperConfig;
import com.moneysplitter.controller.data.ParticipantCreateRequest;
import com.moneysplitter.controller.data.ParticipantResponse;
import com.moneysplitter.controller.data.ParticipantUpdateRequest;
import com.moneysplitter.dao.postgresql.entity.ParticipantEntity;
import com.moneysplitter.model.ParticipantUpdateData;
import com.moneysplitter.model.PartyParticipant;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(config = MapperConfig.class)
public interface ParticipantMapper {

    ParticipantResponse toResponse(PartyParticipant participant);

    List<ParticipantResponse> toResponses(List<PartyParticipant> participant);

    @Mapping(target = "id", ignore = true)
    PartyParticipant fromCreateRequest(ParticipantCreateRequest request);

    ParticipantUpdateData fromUpdateRequest(ParticipantUpdateRequest request);

    ParticipantEntity toEntity(PartyParticipant participant);

    PartyParticipant fromEntity(ParticipantEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "partyId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFields(ParticipantUpdateData participantUpdateData, @MappingTarget PartyParticipant participantTarget);
}
