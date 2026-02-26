package com.moneysplitter.controller;

import com.moneysplitter.controller.data.TransactionStatusUpdateRequest;
import com.moneysplitter.model.TransactionStatus;
import com.moneysplitter.service.SplitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transactions")
@RequiredArgsConstructor
public class TransactionControllerImpl implements TransactionController {

    private final SplitterService splitterService;

    @PutMapping
    @Override
    public void updateTransactionStatus(@RequestBody TransactionStatusUpdateRequest request) {
        splitterService.updateTransactionStatus(request.id(), TransactionStatus.valueOf(request.status().name()));
    }
}
