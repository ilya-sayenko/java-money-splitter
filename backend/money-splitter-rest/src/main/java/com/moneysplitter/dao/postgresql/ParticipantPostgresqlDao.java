package com.moneysplitter.dao.postgresql;

import com.moneysplitter.dao.ParticipantDao;
import com.moneysplitter.dao.postgresql.entity.ParticipantEntity;
import com.moneysplitter.dao.postgresql.repository.ParticipantRepository;
import com.moneysplitter.mapper.ParticipantMapper;
import com.moneysplitter.model.PartyParticipant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ParticipantPostgresqlDao implements ParticipantDao {

    private final ParticipantRepository participantRepository;

    private final ParticipantMapper participantMapper;

    @Override
    public PartyParticipant saveParticipant(PartyParticipant participant) {
        ParticipantEntity participantEntity = participantMapper.toEntity(participant);
        participantEntity = participantRepository.save(participantEntity);

        return participantMapper.fromEntity(participantEntity);
    }

    @Override
    public void deleteParticipantById(UUID participantId) {
        participantRepository.deleteById(participantId);
    }

    @Override
    public Optional<PartyParticipant> findParticipantById(UUID id) {
        return participantRepository.findById(id).map(participantMapper::fromEntity);
    }

    @Override
    public List<PartyParticipant> findParticipantsByPartyId(UUID partyId) {
        return participantRepository.findByPartyId(partyId)
                .stream()
                .map(participantMapper::fromEntity)
                .collect(Collectors.toList());
    }
}
