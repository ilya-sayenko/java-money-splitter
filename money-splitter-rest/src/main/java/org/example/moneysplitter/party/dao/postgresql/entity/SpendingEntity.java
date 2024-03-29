package org.example.moneysplitter.party.dao.postgresql.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
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
