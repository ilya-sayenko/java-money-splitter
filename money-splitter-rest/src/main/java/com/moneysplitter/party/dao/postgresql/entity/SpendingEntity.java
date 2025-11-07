package com.moneysplitter.party.dao.postgresql.entity;

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
import org.hibernate.annotations.GenericGenerator;

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
    @Column(name = "spnd_id")
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator") // TODO
    private UUID id;

    @Column(name = "party_party_id")
    UUID partyId;

    @Column(name = "prnt_prnt_id")
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
