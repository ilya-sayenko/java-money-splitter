package com.moneysplitter.controller;

import com.moneysplitter.controller.data.SpendingCreateRequest;
import com.moneysplitter.mapper.ParticipantMapper;
import com.moneysplitter.mapper.PartyMapper;
import com.moneysplitter.mapper.SpendingMapper;
import com.moneysplitter.mapper.TransactionMapper;
import com.moneysplitter.model.PartySpending;
import com.moneysplitter.service.SplitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/spendings")
@RequiredArgsConstructor
public class SpendingsControllerImpl implements SpendingsController {

    private final SplitterService splitterService;

    private final PartyMapper partyMapper;

    private final ParticipantMapper participantMapper;

    private final TransactionMapper transactionMapper;

    private final SpendingMapper spendingMapper;

    @PostMapping
    @Override
    public UUID createSpending(@RequestBody SpendingCreateRequest request) {
        PartySpending spending = spendingMapper.fromCreateRequest(request);
        return splitterService.createSpending(spending);
    }

    @DeleteMapping("/{spendingId}")
    @Override
    public void deleteSpendingById(@PathVariable UUID spendingId) {
        splitterService.deleteSpendingById(spendingId);
    }
}
