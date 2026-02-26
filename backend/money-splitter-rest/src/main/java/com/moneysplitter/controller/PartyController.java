package com.moneysplitter.controller;

import com.moneysplitter.controller.data.PartyCreateRequest;
import com.moneysplitter.controller.data.ParticipantResponse;
import com.moneysplitter.controller.data.PartyResponse;
import com.moneysplitter.controller.data.PartyUpdateRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface PartyController {

    PartyResponse getPartyById(UUID partyId);

    @GetMapping
    List<PartyResponse> getAllPartyById(@RequestParam("ids") List<UUID> partyIds);

    UUID createParty(PartyCreateRequest partyRequestDto);

    void updateParty(PartyUpdateRequest partyUpdateRequest);

    List<ParticipantResponse> findParticipantsByPartyId(UUID partyId);
}
