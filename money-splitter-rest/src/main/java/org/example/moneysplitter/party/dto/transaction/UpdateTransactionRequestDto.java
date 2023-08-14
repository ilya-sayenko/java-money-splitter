package org.example.moneysplitter.party.dto.transaction;

import lombok.Getter;

@Getter
public class UpdateTransactionRequestDto {
    Operation operation;
    String value;

    public enum Operation {
        UPDATE_STATUS
    }
}

