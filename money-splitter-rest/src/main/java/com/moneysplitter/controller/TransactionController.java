package com.moneysplitter.controller;

import com.moneysplitter.controller.data.TransactionStatusUpdateRequest;

public interface TransactionController {

    void updateTransactionStatus(TransactionStatusUpdateRequest request);
}
