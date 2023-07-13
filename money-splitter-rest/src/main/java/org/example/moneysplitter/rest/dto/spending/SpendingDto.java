package org.example.moneysplitter.rest.dto.spending;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class SpendingDto {
    private UUID id;
    private UUID payerId;
    private String name;
    private BigDecimal amount;
    private Split split;
    private Map<UUID, BigDecimal> amounts;

    @Getter
    @Builder
    public static class Split {
        private String splitType;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Map<UUID, BigDecimal> participants;
    }
}
