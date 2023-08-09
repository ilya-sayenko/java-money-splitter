package org.example.moneysplitter.rest.dao.postgresql.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "proportions")
public class ProportionEntity {
    @Id
    @Column(name = "prop_id")
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "spnd_spnd_id")
    @Setter
    private SpendingEntity spending;

    @ManyToOne
    @JoinColumn(name = "prnt_prnt_id")
    private ParticipantEntity payer;

    @Column(name = "proportion")
    private BigDecimal proportion;

    @Column(name = "amount")
    private BigDecimal amount;
}
