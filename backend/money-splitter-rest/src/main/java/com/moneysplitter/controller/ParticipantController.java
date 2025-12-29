package com.moneysplitter.controller;

import com.moneysplitter.controller.data.ParticipantCreateRequest;
import com.moneysplitter.controller.data.ParticipantUpdateRequest;

import java.util.UUID;

public interface ParticipantController {

    void updateParticipant(ParticipantUpdateRequest request);

    UUID createParticipant(ParticipantCreateRequest request);

    void deleteParticipantById(UUID participantId);

}
