package com.moneysplitter.controller;

import com.moneysplitter.controller.data.SpendingCreateRequest;
import com.moneysplitter.controller.data.SpendingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface SpendingsController {

    @GetMapping
    List<SpendingResponse> findSpendings(@RequestParam("partyIds") List<UUID> partyIds);

    UUID createSpending(SpendingCreateRequest request);

    void deleteSpendingById(UUID spendingId);
}
