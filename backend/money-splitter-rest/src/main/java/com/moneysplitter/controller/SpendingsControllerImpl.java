package com.moneysplitter.controller;

import com.moneysplitter.controller.data.SpendingCreateRequest;
import com.moneysplitter.controller.data.SpendingResponse;
import com.moneysplitter.mapper.SpendingMapper;
import com.moneysplitter.model.PartySpending;
import com.moneysplitter.service.SplitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/spendings")
@RequiredArgsConstructor
public class SpendingsControllerImpl implements SpendingsController {

    private final SplitterService splitterService;

    private final SpendingMapper spendingMapper;

    @GetMapping
    @Override
    public List<SpendingResponse> findSpendings(@RequestParam("partyIds") List<UUID> partyIds) {
        return spendingMapper.toResponses(splitterService.findAllSpendingsByPartyId(partyIds));
    }

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
