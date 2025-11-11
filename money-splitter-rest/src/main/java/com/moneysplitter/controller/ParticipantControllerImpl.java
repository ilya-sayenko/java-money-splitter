package com.moneysplitter.controller;

import com.moneysplitter.controller.data.ParticipantCreateRequest;
import com.moneysplitter.controller.data.ParticipantUpdateRequest;
import com.moneysplitter.mapper.ParticipantMapper;
import com.moneysplitter.model.ParticipantUpdateData;
import com.moneysplitter.model.PartyParticipant;
import com.moneysplitter.service.SplitterService;
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

    private final SplitterService splitterService;

    private final ParticipantMapper participantMapper;

    @PutMapping
    @Override
    public void updateParticipant(@Valid @RequestBody ParticipantUpdateRequest request) {
        ParticipantUpdateData updateData = participantMapper.fromUpdateRequest(request);
        splitterService.updateParticipant(updateData);
    }

    @PostMapping
    @Override
    public UUID createParticipant(@Valid @RequestBody ParticipantCreateRequest request) {
        PartyParticipant participant = participantMapper.fromCreateRequest(request);
        return splitterService.createParticipant(participant);
    }

    @DeleteMapping("/{participantId}")
    @Override
    public void deleteParticipantById(@PathVariable UUID participantId) {
        splitterService.deleteParticipantById(participantId);
    }
}
