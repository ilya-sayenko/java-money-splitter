package com.moneysplitter.party.controller;

import com.moneysplitter.party.dto.participant.ParticipantDto;
import com.moneysplitter.party.dto.party.CreatePartyRequestDto;
import com.moneysplitter.party.dto.party.PartyDto;
import com.moneysplitter.party.dto.spending.SpendingDto;
import com.moneysplitter.party.dto.transaction.TransactionDto;
import com.moneysplitter.party.dto.transaction.UpdateTransactionRequestDto;
import com.moneysplitter.party.mapper.ParticipantMapper;
import com.moneysplitter.party.mapper.PartyMapper;
import com.moneysplitter.party.mapper.SpendingMapper;
import com.moneysplitter.party.mapper.TransactionMapper;
import com.moneysplitter.party.model.Party;
import com.moneysplitter.party.model.PartyParticipant;
import com.moneysplitter.party.model.PartySpending;
import com.moneysplitter.party.model.PartyTransaction;
import com.moneysplitter.party.service.PartyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/parties")
@RequiredArgsConstructor
@Validated
public class PartyController {

    private final PartyService partyService;

    private final PartyMapper partyMapper;

    private final ParticipantMapper participantMapper;

    private final TransactionMapper transactionMapper;

    private final SpendingMapper spendingMapper;

    @GetMapping(value = "/{partyId}")
    public PartyDto getPartyById(@PathVariable UUID partyId) {
        return partyMapper.toDTO(partyService.findPartyById(partyId));
    }

    @PostMapping
    public PartyDto createParty(@Valid @RequestBody CreatePartyRequestDto partyRequestDto) {
        Party party = partyMapper.fromCreateRequestDTO(partyRequestDto);
        return partyMapper.toDTO(partyService.saveParty(party));
    }

    @PutMapping(value = "/{partyId}/participants/{participantId}")
    public ResponseEntity<ParticipantDto> updateParticipant(
            @PathVariable UUID partyId,
            @PathVariable UUID participantId,
            @Valid @RequestBody ParticipantDto participantDto
    ) {
        PartyParticipant participant = participantMapper
                .fromDTO(participantDto.withId(participantId))
                .withPartyId(partyId);
        participant = partyService.saveParticipant(participant);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(participantMapper.toDTO(participant));
    }

    @PostMapping(value = "/{partyId}/participants")
    public ResponseEntity<ParticipantDto> createParticipant(
            @PathVariable UUID partyId,
            @Valid @RequestBody ParticipantDto participantDto
    ) {
        PartyParticipant participant = participantMapper
                .fromDTO(participantDto)
                .withPartyId(partyId);
        participant = partyService.saveParticipant(participant);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(participantMapper.toDTO(participant));
    }

    @GetMapping(value = "/{partyId}/participants")
    public ResponseEntity<List<ParticipantDto>> findParticipants(@PathVariable UUID partyId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(participantMapper.toDTOs(partyService.findParticipantsByPartyId(partyId)));
    }

    @DeleteMapping(value = "/{partyId}/participants/{participantId}")
    public ResponseEntity<Void> deleteParticipant(
            @PathVariable UUID partyId,
            @PathVariable UUID participantId
    ) {
        partyService.deleteParticipant(partyId, participantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{partyId}/spendings")
    public List<SpendingDto> getSpendings(@PathVariable UUID partyId) {
        return spendingMapper.toDTOs(partyService.findSpendingsByPartyId(partyId));
    }

    @PostMapping(value = "/{partyId}/spendings")
    public SpendingDto createSpending(
            @PathVariable UUID partyId,
            @RequestBody SpendingDto request
    ) {
        PartySpending spending = spendingMapper.fromDTO(request).withPartyId(partyId);
        return spendingMapper.toDTO(partyService.saveSpending(spending));
    }

    @DeleteMapping(value = "/{partyId}/spendings/{spendingId}")
    public ResponseEntity<Void> deleteSpending(
            @PathVariable UUID partyId,
            @PathVariable UUID spendingId
    ) {
        partyService.deleteSpending(partyId, spendingId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{partyId}/transactions")
    public List<TransactionDto> getTransactions(@PathVariable UUID partyId) {
        return transactionMapper.toDTOs(partyService.findTransactionsByPartyId(partyId));
    }

    @PatchMapping(value = "/{partyId}/transactions/{transactionId}")
    public ResponseEntity<Void> updateTransactionStatus(
            @PathVariable UUID partyId,
            @PathVariable UUID transactionId,
            @RequestBody UpdateTransactionRequestDto request
    ) {
        switch (request.getOperation()) {
            case UPDATE_STATUS:
                partyService.updateTransactionStatus(partyId, transactionId, PartyTransaction.Status.valueOf(request.getValue()));
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            default:
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
}
