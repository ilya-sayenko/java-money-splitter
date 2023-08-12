package org.example.moneysplitter.rest.mapper;

import org.example.moneysplitter.rest.dao.postgresql.entity.PartyEntity;
import org.example.moneysplitter.rest.dto.party.CreatePartyRequestDto;
import org.example.moneysplitter.rest.dto.party.PartyDto;
import org.example.moneysplitter.rest.model.Party;
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
