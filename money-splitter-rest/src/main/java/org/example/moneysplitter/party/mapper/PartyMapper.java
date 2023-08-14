package org.example.moneysplitter.party.mapper;

import org.example.moneysplitter.party.dao.postgresql.entity.PartyEntity;
import org.example.moneysplitter.party.dto.party.CreatePartyRequestDto;
import org.example.moneysplitter.party.dto.party.PartyDto;
import org.example.moneysplitter.party.model.Party;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartyMapper {
    PartyDto toDTO(Party party);

    PartyEntity toEntity(Party party);

    Party fromEntity(PartyEntity partyEntity);

    @Mapping(target = "totalAmount", constant = "0")
    Party fromCreateRequestDTO(CreatePartyRequestDto requestDto);
}
