package org.example.moneysplitter.rest.dto.transaction;

import lombok.Getter;

@Getter
public class UpdateTransactionRequestDto {
    Operation operation;
    String value;

    public enum Operation {
        UPDATE_STATUS
    }
}

