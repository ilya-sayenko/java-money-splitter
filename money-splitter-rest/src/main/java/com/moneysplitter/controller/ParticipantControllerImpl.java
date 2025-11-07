package com.moneysplitter.controller;

import com.moneysplitter.controller.data.ParticipantCreateRequest;
import com.moneysplitter.controller.data.ParticipantResponse;
import com.moneysplitter.controller.data.ParticipantUpdateRequest;
import com.moneysplitter.mapper.ParticipantMapper;
import com.moneysplitter.model.ParticipantUpdateData;
import com.moneysplitter.model.PartyParticipant;
import com.moneysplitter.service.PartyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/participants")
@RequiredArgsConstructor
public class ParticipantControllerImpl implements ParticipantController {

    private final PartyService partyService;

    private final ParticipantMapper participantMapper;

    @PutMapping
    @Override
    public ParticipantResponse updateParticipant(@Valid @RequestBody ParticipantUpdateRequest request) {
        ParticipantUpdateData updateData = participantMapper.fromUpdateRequest(request);
        return participantMapper.toResponse(partyService.updateParticipant(updateData));
    }

    @PostMapping
    @Override
    public ParticipantResponse createParticipant(@Valid @RequestBody ParticipantCreateRequest request) {
        PartyParticipant participant = participantMapper.fromCreateRequest(request);
        return participantMapper.toResponse(partyService.createParticipant(participant));
    }

    @DeleteMapping("/{participantId}")
    @Override
    public void deleteParticipantById(@PathVariable UUID participantId) {
        partyService.deleteParticipantById(participantId);
    }
}
