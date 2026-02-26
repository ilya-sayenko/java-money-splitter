package com.moneysplitter.dao.postgresql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "proportions")
public class ProportionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Setter
    @Column(name = "spending_id")
    private UUID spendingId;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private ParticipantEntity participant;

    @Column(name = "proportion")
    private BigDecimal proportion;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "created_at")
    @CreationTimestamp
    private OffsetDateTime createDate;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private OffsetDateTime updateDate;
}
