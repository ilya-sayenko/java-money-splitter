package org.example.moneysplitter.rest.dto.spending;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Getter
public class UpdateSpendingRequestDto {
    private UUID id;
    private UUID payerId;
    private String name;
    private BigDecimal amount;
    private Split split;

    @Getter
    public static class Split {
        private String splitType;
        private Map<UUID, BigDecimal> participants;
    }
}
