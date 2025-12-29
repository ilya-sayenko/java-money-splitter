package com.moneysplitter.controller;

import com.moneysplitter.controller.data.SpendingCreateRequest;

import java.util.UUID;

public interface SpendingsController {

    UUID createSpending(SpendingCreateRequest request);

    void deleteSpendingById(UUID spendingId);
}
