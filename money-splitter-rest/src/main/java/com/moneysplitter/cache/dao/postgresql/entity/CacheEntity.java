package com.moneysplitter.cache.dao.postgresql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

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
