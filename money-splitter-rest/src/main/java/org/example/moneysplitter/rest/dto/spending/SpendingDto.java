package org.example.moneysplitter.rest.dto.spending;

import lombok.Value;

@Value
public class SpendingDto {
    String id;
    String name;
    String amount;
    String splitType;

}
