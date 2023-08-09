package org.example.moneysplitter.rest.controller;

import lombok.RequiredArgsConstructor;
import org.example.moneysplitter.rest.dto.participant.ParticipantDto;
import org.example.moneysplitter.rest.dto.spending.SpendingDto;
import org.example.moneysplitter.rest.dto.party.CreatePartyRequestDto;
import org.example.moneysplitter.rest.dto.party.PartyDto;
import org.example.moneysplitter.rest.dto.transaction.TransactionDto;
import org.example.moneysplitter.rest.dto.transaction.UpdateTransactionStatusRequestDto;
import org.example.moneysplitter.rest.mapper.*;
import org.example.moneysplitter.rest.model.Party;
import org.example.moneysplitter.rest.model.PartyParticipant;
import org.example.moneysplitter.rest.model.PartySpending;
import org.example.moneysplitter.rest.model.PartyTransaction;
import org.example.moneysplitter.rest.service.PartyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return partyMapper.toDto(partyService.findPartyById(partyId));
    }

    @PostMapping
    public PartyDto createParty(@Valid @RequestBody CreatePartyRequestDto partyRequestDto) {
        Party party = partyMapper.fromCreateRequest(partyRequestDto);
        return partyMapper.toDto(partyService.saveParty(party));
    }

    @PutMapping(value = "/{partyId}/participants/{participantId}")
    public ResponseEntity<ParticipantDto> updateParticipant(
            @PathVariable UUID partyId,
            @PathVariable UUID participantId,
            @Valid @RequestBody ParticipantDto participantDto
    ) {
        PartyParticipant participant = participantMapper
                .fromDto(participantDto.withId(participantId))
                .withPartyId(partyId);
        participant = partyService.saveParticipant(participant);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(participantMapper.toDto(participant));
    }

    @PostMapping(value = "/{partyId}/participants")
    public ResponseEntity<ParticipantDto> createParticipant(
            @PathVariable UUID partyId,
            @Valid @RequestBody ParticipantDto participantDto
    ) {
        PartyParticipant participant = participantMapper
                .fromDto(participantDto)
                .withPartyId(partyId);
        participant = partyService.saveParticipant(participant);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(participantMapper.toDto(participant));
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
//        return partyService.findSpendingsByPartyId(partyId)
//                .stream()
//                .map(SpendingMapper::toDto)
//                .collect(Collectors.toUnmodifiableList());
        return spendingMapper.toDto(partyService.findSpendingsByPartyId(partyId));
    }

    @PostMapping(value = "/{partyId}/spendings")
    public SpendingDto createSpending(
            @PathVariable UUID partyId,
            @RequestBody SpendingDto request
    ) {
//        PartySpending spending = SpendingMapper1.fromRequestDto(request).withPartyId(partyId);
//        return SpendingMapper1.toDto(partyService.saveSpending(spending));
        PartySpending spending = spendingMapper.fromDto(request).withPartyId(partyId);
        return spendingMapper.toDto(partyService.saveSpending(spending));
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
        return transactionMapper.toDto(partyService.findTransactionsByPartyId(partyId));
    }

    @PatchMapping(value = "/{partyId}/transactions/{transactionId}")
    public void updateTransactionStatus(
            @PathVariable UUID partyId,
            @PathVariable UUID transactionId,
            @RequestBody UpdateTransactionStatusRequestDto request
    ) {
        partyService.updateTransactionStatus(partyId, transactionId, PartyTransaction.Status.valueOf(request.getStatus()));
    }
}
