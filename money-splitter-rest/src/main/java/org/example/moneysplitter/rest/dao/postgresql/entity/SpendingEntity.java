package org.example.moneysplitter.rest.dao.postgresql.entity;

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

    @ManyToOne
    @JoinColumn(name = "party_party_id")
    PartyEntity party;

    @ManyToOne
    @JoinColumn(name = "prnt_prnt_id")
    PartyParticipantEntity payer;

    @Column(name = "name")
    String name;

    @Column(name = "amount")
    BigDecimal amount;

    @Column(name = "sptp_sptp_id")
    SplitType splitType;

    @Setter
    @Transient
    @Builder.Default
    List<ProportionEntity> proportions = new ArrayList<>();

    public enum SplitType {
        EQUAL, AMOUNT, PARTITION
    }
}
