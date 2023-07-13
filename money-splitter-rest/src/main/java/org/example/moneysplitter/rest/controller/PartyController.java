package org.example.moneysplitter.rest.controller;

import lombok.RequiredArgsConstructor;
import org.example.moneysplitter.rest.dto.spending.SpendingDto;
import org.example.moneysplitter.rest.dto.participant.PartyParticipantDto;
import org.example.moneysplitter.rest.dto.participant.UpdatePartyParticipantRequestDto;
import org.example.moneysplitter.rest.dto.party.CreatePartyRequestDto;
import org.example.moneysplitter.rest.dto.party.PartyDto;
import org.example.moneysplitter.rest.dto.spending.UpdateSpendingRequestDto;
import org.example.moneysplitter.rest.dto.transaction.TransactionDto;
import org.example.moneysplitter.rest.dto.transaction.UpdateTransactionStatusRequestDto;
import org.example.moneysplitter.rest.mapper.ParticipantMapper;
import org.example.moneysplitter.rest.mapper.PartyMapper;
import org.example.moneysplitter.rest.mapper.SpendingMapper;
import org.example.moneysplitter.rest.mapper.TransactionMapper;
import org.example.moneysplitter.rest.model.Party;
import org.example.moneysplitter.rest.model.PartyParticipant;
import org.example.moneysplitter.rest.model.PartySpending;
import org.example.moneysplitter.rest.model.PartyTransaction;
import org.example.moneysplitter.rest.service.PartyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parties")
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyService;

    @GetMapping(value = "/{partyId}")
    public PartyDto getPartyById(@PathVariable UUID partyId) {
        return PartyMapper.toDto(partyService.findById(partyId));
    }

    @PostMapping
    public PartyDto createParty(@RequestBody CreatePartyRequestDto partyRequestDto) {
        Party party = Party
                .builder()
                .name(partyRequestDto.getName())
                .description(partyRequestDto.getDescription())
                .totalAmount(BigDecimal.ZERO)
                .build();

        return PartyMapper.toDto(partyService.save(party));
    }

    @PutMapping(value = "/{partyId}/participants")
    public ResponseEntity<PartyParticipantDto> putParticipant(
            @PathVariable UUID partyId,
            @RequestBody UpdatePartyParticipantRequestDto participantDto
    ) {
        PartyParticipant participant = PartyParticipant
                .builder()
                .id(participantDto.getId())
                .name(participantDto.getName())
                .partyId(partyId)
                .build();

        PartyParticipant savedParticipant = partyService.saveParticipant(participant);

        HttpStatus status = HttpStatus.OK;

//        if (participantDto.getId() == null) {
//            status = HttpStatus.CREATED;
//        } else if (participantDto.getId().equals(savedParticipant.getId())) {
//            status = HttpStatus.OK;
//        } else {
//            status = HttpStatus.CREATED;
//        }

        return ResponseEntity
                .status(status)
                .body(ParticipantMapper.toDto(savedParticipant));
    }

    @DeleteMapping(value = "/{partyId}/participants/{participantId}")
    public void deleteParticipant(
            @PathVariable UUID partyId,
            @PathVariable UUID participantId
    ) {
        partyService.deleteParticipant(partyId, participantId);
    }

    @GetMapping(value = "/{partyId}/spendings")
    public List<SpendingDto> getSpendings(@PathVariable UUID partyId) {
        return partyService.findSpendingsByPartyId(partyId)
                .stream()
                .map(SpendingMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @PutMapping(value = "/{partyId}/spendings")
    public SpendingDto putSpending(
            @PathVariable UUID partyId,
            @RequestBody UpdateSpendingRequestDto request
    ) {
        PartySpending spending = SpendingMapper.fromRequestDto(request).withPartyId(partyId);

        return SpendingMapper.toDto(partyService.saveSpending(spending));
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
        return partyService.findTransactionsByPartyId(partyId)
                .stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
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
