package com.moneysplitter.party.dao.postgresql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

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
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator") // TODO
    private UUID id;

    @Column(name = "party_party_id")
    private UUID partyId;

    @Column(name = "from_prnt_id")
    private UUID payerId;

    @Column(name = "to_prnt_id")
    private UUID payeeId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING, CLOSED
    }
}
