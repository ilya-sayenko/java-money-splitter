package com.moneysplitter.dao.postgresql;

import com.moneysplitter.dao.PartyDao;
import com.moneysplitter.dao.postgresql.entity.PartyEntity;
import com.moneysplitter.dao.postgresql.repository.PartyRepository;
import com.moneysplitter.mapper.PartyMapper;
import com.moneysplitter.model.Party;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
public class PartyPostgresDao implements PartyDao {

    private final PartyRepository partyRepository;

    private final PartyMapper partyMapper;

    @Override
    public Optional<Party> findPartyById(UUID id) {
//        final Optional<Party> cachedParty = partyCache.fetchPartyFromCache(id);
//        if (cachedParty.isPresent()) {
//            return cachedParty;
//        }
        final Optional<Party> party = partyRepository.findById(id).map(partyMapper::fromEntity);
        if (party.isPresent()) {
//            partyCache.putPartyToCache(party.get());
            return party;
        }
        return Optional.empty();
    }

    @Override
    public Party saveParty(Party party) {
        PartyEntity partyEntity = partyMapper.toEntity(party);
        partyRepository.save(partyEntity);

        return partyMapper.fromEntity(partyEntity);
    }
}
