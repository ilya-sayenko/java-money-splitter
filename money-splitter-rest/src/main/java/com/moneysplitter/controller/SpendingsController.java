package com.moneysplitter.controller;

import com.moneysplitter.controller.data.ParticipantResponse;
import com.moneysplitter.controller.data.PartyCreateRequest;
import com.moneysplitter.controller.data.PartyResponse;
import com.moneysplitter.controller.data.SpendingCreateRequest;
import com.moneysplitter.controller.data.SpendingResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface SpendingsController {

    SpendingResponse createSpending(SpendingCreateRequest request);

    void deleteSpendingById(UUID spendingId);
//
//    List<TransactionResponse> getTransactions(UUID partyId);
//
//    ResponseEntity<Void> updateTransactionStatus(UUID partyId, UUID transactionId, UpdateTransactionStatusRequest request);
}
