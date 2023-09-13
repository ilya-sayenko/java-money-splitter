package org.example.moneysplitter.cache.model;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Cache {
    private UUID id;
    private Type type;
    private String data;

    public enum Type {
        PARTY
    }
}
