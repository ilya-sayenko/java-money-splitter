package com.moneysplitter.service;

import com.moneysplitter.controller.data.PartyWithAggregatesResponse;

import java.util.List;
import java.util.UUID;

public interface AggregatesResponseService {

    List<PartyWithAggregatesResponse> getAllAggregatedPartiesById(List<UUID> partyIds);
}
