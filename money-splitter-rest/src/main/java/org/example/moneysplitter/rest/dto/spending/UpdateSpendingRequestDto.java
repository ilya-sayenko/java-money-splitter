package org.example.moneysplitter.rest.dto.spending;

import lombok.Value;

@Value
public class UpdateSpendingRequestDto {
    String id;
    String name;
    String amount;
    String split;
}
