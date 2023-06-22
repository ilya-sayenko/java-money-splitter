package org.example.moneysplitter.rest.controller;

import lombok.RequiredArgsConstructor;
import org.example.moneysplitter.rest.dto.spending.SpendingDto;
import org.example.moneysplitter.rest.dto.participant.PartyParticipantDto;
import org.example.moneysplitter.rest.dto.participant.UpdatePartyParticipantRequestDto;
import org.example.moneysplitter.rest.dto.party.CreatePartyRequestDto;
import org.example.moneysplitter.rest.dto.party.PartyDto;
import org.example.moneysplitter.rest.dto.spending.UpdateSpendingRequestDto;
import org.example.moneysplitter.rest.dto.transaction.TransactionDto;
import org.example.moneysplitter.rest.service.PartyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parties")
@RequiredArgsConstructor
public class PartyController {
    PartyService partyService;

    @GetMapping(value = "/{partyId}")
    public PartyDto getPartyById(@PathVariable String partyId) {
        return null;
    }

    @PostMapping
    public PartyDto createParty(@RequestBody CreatePartyRequestDto party) {
        return null;
    }

    @PutMapping(value = "/{partyId}/participants")
    public PartyParticipantDto createParticipant(@RequestBody UpdatePartyParticipantRequestDto participant) {
        return null;
    }

    @DeleteMapping(value = "/{partyId}/participants/{participantId}")
    public void deleteParticipant(
            @PathVariable String partyId,
            @PathVariable String participantId) {

    }

    @GetMapping(value = "/{partyId}/spendings")
    public List<SpendingDto> getSpendings(@PathVariable String partyId) {
        return null;
    }

    @PutMapping(value = "/{partyId}/spendings")
    public SpendingDto updateSpending(
            @PathVariable String partyId,
            @RequestBody UpdateSpendingRequestDto request) {
        return null;
    }

    @DeleteMapping(value = "/{partyId}/spendings/{spendingId}")
    public SpendingDto getSpendings(
            @PathVariable String partyId,
            @PathVariable String spendingId) {
        return null;
    }

    @GetMapping(value = "/{partyId}/transactions")
    public List<TransactionDto> getTransactions(@PathVariable String partyId) {
        return null;
    }

    @PatchMapping(value = "/{partyId}/transactions/{transactionId}")
    public void updateTransactionStatus(
            @PathVariable String partyId,
            @PathVariable String transactionId,
            @RequestBody UpdateSpendingRequestDto request) {

    }
}
