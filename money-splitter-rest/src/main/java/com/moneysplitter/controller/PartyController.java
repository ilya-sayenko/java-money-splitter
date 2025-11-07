package com.moneysplitter.controller;

import com.moneysplitter.controller.data.PartyCreateRequest;
import com.moneysplitter.controller.data.ParticipantResponse;
import com.moneysplitter.controller.data.PartyResponse;
import com.moneysplitter.controller.data.PartyUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface PartyController {

    PartyResponse getPartyById(UUID partyId);

    PartyResponse createParty(PartyCreateRequest partyRequestDto);

    @PutMapping
    PartyResponse updateParty(@Valid @RequestBody PartyUpdateRequest partyUpdateRequest);

    List<ParticipantResponse> findParticipantsByPartyId(UUID partyId);
}
