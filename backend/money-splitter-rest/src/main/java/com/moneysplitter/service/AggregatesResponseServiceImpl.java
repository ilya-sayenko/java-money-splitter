package com.moneysplitter.service;

import com.moneysplitter.controller.data.PartyWithAggregatesResponse;
import com.moneysplitter.dao.PartyDao;
import com.moneysplitter.mapper.PartyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AggregatesResponseServiceImpl implements AggregatesResponseService {

    private final PartyDao partyDao;

    private final PartyMapper partyMapper;

    @Override
    public List<PartyWithAggregatesResponse> getAllAggregatedPartiesById(List<UUID> partyIds) {
        return partyMapper.toAggregatedResponses(partyDao.findAllPartyWithCollectionsById(partyIds));
    }
}
