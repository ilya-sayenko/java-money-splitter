package org.example.moneysplitter.rest.mapper;

import org.example.moneysplitter.rest.dao.postgresql.entity.ParticipantEntity;
import org.example.moneysplitter.rest.dto.participant.ParticipantDto;
import org.example.moneysplitter.rest.model.PartyParticipant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {
    ParticipantDto toDto(PartyParticipant participant);
    PartyParticipant fromDto(ParticipantDto participantDto);

    ParticipantEntity toEntity(PartyParticipant participant);

    PartyParticipant fromEntity(ParticipantEntity entity);
}
