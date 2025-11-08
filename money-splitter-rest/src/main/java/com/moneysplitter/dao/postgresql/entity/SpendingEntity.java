package com.moneysplitter.dao.postgresql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "spendings")
public class SpendingEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "party_id")
    UUID partyId;

    @Column(name = "payer_id")
    UUID payerId;

    @Column(name = "name")
    String name;

    @Column(name = "amount")
    BigDecimal amount;

    @Column(name = "split_type")
    @Enumerated(EnumType.STRING)
    SplitType splitType;

    @Setter
    @Transient
    @Builder.Default
    List<ProportionEntity> proportions = new ArrayList<>();

    public enum SplitType {
        EQUAL, AMOUNT, PARTITION
    }
}
