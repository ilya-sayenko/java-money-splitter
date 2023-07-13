package org.example.moneysplitter.rest.mapper;

import lombok.experimental.UtilityClass;
import org.example.moneysplitter.rest.dto.participant.PartyParticipantDto;
import org.example.moneysplitter.rest.dao.postgresql.entity.PartyParticipantEntity;
import org.example.moneysplitter.rest.model.PartyParticipant;

@UtilityClass
public class ParticipantMapper {
    public PartyParticipantDto toDto(PartyParticipant participant) {
        return PartyParticipantDto
                .builder()
                .id(participant.getId())
                .name(participant.getName())
                .build();
    }

    public PartyParticipantEntity toEntity(PartyParticipant participant) {
        return PartyParticipantEntity
                .builder()
                .id(participant.getId())
                .name(participant.getName())
                .build();
    }

    public PartyParticipant fromEntity(PartyParticipantEntity entity) {
        return PartyParticipant
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .partyId(entity.getParty().getId())
                .build();
    }

//    public PartyParticipant fromEntityForParty(PartyParticipantEntity entity, Party party) {
//        return PartyParticipant
//                .builder()
//                .id(entity.getId())
//                .name(entity.getName())
//                .partyId(party)
//                .build();
//    }
}
