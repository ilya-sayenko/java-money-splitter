package org.example.moneysplitter.cache.dao.postgresql.entity;

import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cache")
public class CacheEntity {
    @Id
    @Column(name = "entity_id")
    private UUID id;

    @Column(name = "cache_type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "data", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String data;

    public enum Type {
        UNKNOWN, PARTY
    }
}
