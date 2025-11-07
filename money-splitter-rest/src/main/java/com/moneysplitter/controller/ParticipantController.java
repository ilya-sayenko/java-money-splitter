package com.moneysplitter.controller;

import com.moneysplitter.controller.data.ParticipantCreateRequest;
import com.moneysplitter.controller.data.ParticipantResponse;
import com.moneysplitter.controller.data.ParticipantUpdateRequest;

import java.util.UUID;

public interface ParticipantController {

    ParticipantResponse updateParticipant(ParticipantUpdateRequest request);

    ParticipantResponse createParticipant(ParticipantCreateRequest request);

    void deleteParticipantById(UUID participantId);

}
