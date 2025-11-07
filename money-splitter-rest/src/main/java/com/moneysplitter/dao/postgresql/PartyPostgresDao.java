package com.moneysplitter.dao.postgresql;

import com.moneysplitter.dao.PartyDao;
import com.moneysplitter.dao.postgresql.entity.ParticipantEntity;
import com.moneysplitter.dao.postgresql.entity.PartyEntity;
import com.moneysplitter.dao.postgresql.entity.ProportionEntity;
import com.moneysplitter.dao.postgresql.entity.SpendingEntity;
import com.moneysplitter.dao.postgresql.entity.TransactionEntity;
import com.moneysplitter.dao.postgresql.repository.ParticipantRepository;
import com.moneysplitter.dao.postgresql.repository.PartyRepository;
import com.moneysplitter.dao.postgresql.repository.ProportionRepository;
import com.moneysplitter.dao.postgresql.repository.SpendingRepository;
import com.moneysplitter.dao.postgresql.repository.TransactionRepository;
import com.moneysplitter.mapper.ParticipantMapper;
import com.moneysplitter.mapper.PartyMapper;
import com.moneysplitter.mapper.SpendingMapper;
import com.moneysplitter.mapper.TransactionMapper;
import com.moneysplitter.model.Party;
import com.moneysplitter.model.PartyParticipant;
import com.moneysplitter.model.PartySpending;
import com.moneysplitter.model.PartyTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
//        partyCache.putPartyToCache(party.withId(partyEntity.getId())); // TODO убрать в сервисный слой

        return partyMapper.fromEntity(partyEntity);
    }
}
