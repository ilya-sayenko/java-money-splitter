package com.moneysplitter.dao.postgresql;

import com.moneysplitter.dao.PartyDao;
import com.moneysplitter.dao.postgresql.entity.PartyEntity;
import com.moneysplitter.dao.postgresql.repository.PartyRepository;
import com.moneysplitter.mapper.PartyMapper;
import com.moneysplitter.model.Party;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PartyPostgresDao implements PartyDao {

    private final PartyRepository partyRepository;

    private final PartyMapper partyMapper;

    @Override
    public Optional<Party> findPartyById(UUID id) {
        return partyRepository.findById(id).map(partyMapper::fromEntity);
    }

    @Override
    public Party saveParty(Party party) {
        PartyEntity partyEntity = partyMapper.toEntity(party);
        partyEntity = partyRepository.save(partyEntity);

        return partyMapper.fromEntity(partyEntity);
    }

    @Override
    public List<Party> findAllPartyById(List<UUID> partyIds) {
        return partyMapper.fromEntities(partyRepository.findByIdInOrderByCreateDateDesc(partyIds));
    }
}
