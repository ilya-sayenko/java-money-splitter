package com.moneysplitter.controller;

import com.moneysplitter.controller.data.TransactionStatusUpdateRequest;
import com.moneysplitter.mapper.ParticipantMapper;
import com.moneysplitter.mapper.PartyMapper;
import com.moneysplitter.mapper.SpendingMapper;
import com.moneysplitter.mapper.TransactionMapper;
import com.moneysplitter.model.PartyTransaction;
import com.moneysplitter.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transactions")
@RequiredArgsConstructor
public class TransactionControllerImpl implements TransactionController {

    private final PartyService partyService;

    @PutMapping
    @Override
    public void updateTransactionStatus(@RequestBody TransactionStatusUpdateRequest request) {
        partyService.updateTransactionStatus(request.id(), PartyTransaction.Status.valueOf(request.status()));
    }
}
