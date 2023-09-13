package org.example.moneysplitter.cache.dao.postgresql.entity;

import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private Type type;

    @Column(name = "data", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String data;

    public enum Type {
        PARTY
    }
}
