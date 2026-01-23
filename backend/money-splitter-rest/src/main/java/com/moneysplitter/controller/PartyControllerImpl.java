package com.moneysplitter.controller;

import com.moneysplitter.controller.data.ParticipantResponse;
import com.moneysplitter.controller.data.PartyCreateRequest;
import com.moneysplitter.controller.data.PartyResponse;
import com.moneysplitter.controller.data.PartyUpdateRequest;
import com.moneysplitter.controller.data.SpendingResponse;
import com.moneysplitter.controller.data.TransactionResponse;
import com.moneysplitter.mapper.ParticipantMapper;
import com.moneysplitter.mapper.PartyMapper;
import com.moneysplitter.mapper.SpendingMapper;
import com.moneysplitter.mapper.TransactionMapper;
import com.moneysplitter.model.Party;
import com.moneysplitter.model.PartyUpdateData;
import com.moneysplitter.service.SplitterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/parties")
@RequiredArgsConstructor
public class PartyControllerImpl implements PartyController {

    private final SplitterService splitterService;

    private final PartyMapper partyMapper;

    private final ParticipantMapper participantMapper;

    private final TransactionMapper transactionMapper;

    private final SpendingMapper spendingMapper;

    @GetMapping("/{partyId}")
    @Override
    public PartyResponse getPartyById(@PathVariable UUID partyId) {
        return partyMapper.toResponse(splitterService.findPartyById(partyId));
    }

    @GetMapping
    @Override
    public List<PartyResponse> getAllPartyById(@RequestParam("ids") List<UUID> partyIds) {
        return partyMapper.toResponses(splitterService.findAllPartyById(partyIds));
    }

    @PostMapping
    @Override
    public UUID createParty(@Valid @RequestBody PartyCreateRequest partyCreateRequest) {
        Party party = partyMapper.fromCreateRequest(partyCreateRequest);
        return splitterService.createParty(party);
    }

    @PutMapping
    @Override
    public void updateParty(@Valid @RequestBody PartyUpdateRequest partyUpdateRequest) {
        PartyUpdateData updateData = partyMapper.fromUpdateRequest(partyUpdateRequest);
        splitterService.updateParty(updateData);
    }

    @GetMapping("/{partyId}/participants")
    @Override
    public List<ParticipantResponse> findParticipantsByPartyId(@PathVariable UUID partyId) {
        return participantMapper.toResponses(splitterService.findParticipantsByPartyId(partyId));
    }

    @GetMapping(value = "/{partyId}/spendings")
    public List<SpendingResponse> getSpendingsByPartyId(@PathVariable UUID partyId) {
        return spendingMapper.toResponses(splitterService.findSpendingsByPartyId(partyId));
    }

    @GetMapping(value = "/{partyId}/transactions")
    public List<TransactionResponse> getTransactionsByPartyId(@PathVariable UUID partyId) {
        return transactionMapper.toResponses(splitterService.findTransactionsByPartyId(partyId));
    }
}
