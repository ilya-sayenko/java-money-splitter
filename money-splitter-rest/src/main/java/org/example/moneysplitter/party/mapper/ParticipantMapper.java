package org.example.moneysplitter.party.mapper;

import org.example.moneysplitter.party.dao.postgresql.entity.ParticipantEntity;
import org.example.moneysplitter.party.dto.participant.ParticipantDto;
import org.example.moneysplitter.party.model.PartyParticipant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {
    ParticipantDto toDTO(PartyParticipant participant);

    List<ParticipantDto> toDTOs(List<PartyParticipant> participant);

    PartyParticipant fromDTO(ParticipantDto participantDto);

    ParticipantEntity toEntity(PartyParticipant participant);

    PartyParticipant fromEntity(ParticipantEntity entity);
}
