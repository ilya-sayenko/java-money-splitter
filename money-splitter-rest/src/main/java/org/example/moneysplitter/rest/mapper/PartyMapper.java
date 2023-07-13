package org.example.moneysplitter.rest.mapper;

import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;
import org.example.moneysplitter.rest.dto.party.PartyDto;
import org.example.moneysplitter.rest.dao.postgresql.entity.PartyEntity;
import org.example.moneysplitter.rest.model.Party;

import java.util.stream.Collectors;

@UtilityClass
public class PartyMapper {
    public PartyDto toDto(Party party) {
        return PartyDto
                .builder()
                .id(party.getId().toString())
                .name(party.getName())
                .description(party.getDescription())
                .totalAmount(party.getTotalAmount())
                .build();
    }

    public PartyEntity toEntity(Party party) {
        return PartyEntity
                .builder()
                .id(party.getId())
                .name(party.getName())
                .description(party.getDescription())
                .totalAmount(party.getTotalAmount())
                .build();
    }

    public Party fromEntity(PartyEntity partyEntity) {
        return Party
                .builder()
                .id(partyEntity.getId())
                .name(partyEntity.getName())
                .description(partyEntity.getDescription())
                .totalAmount(partyEntity.getTotalAmount())
                .build();
    }
}
