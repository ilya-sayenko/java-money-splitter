package org.example.moneysplitter.rest.dao.postgresql.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @Column(name = "tran_id")
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "party_party_id")
    private PartyEntity party;

    @ManyToOne
    @JoinColumn(name = "from_prnt_id")
    private PartyParticipantEntity payer;

    @ManyToOne
    @JoinColumn(name = "to_prnt_id")
    private PartyParticipantEntity payee;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "trst_trst_id")
    private Status status;

    public enum Status {
        PENDING, CLOSED
    }
}
