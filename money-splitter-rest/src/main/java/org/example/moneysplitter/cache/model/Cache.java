package org.example.moneysplitter.cache.model;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Cache<T> {
    private UUID id;
    private T data;
}
