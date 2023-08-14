package org.example.moneysplitter.party.dao.postgresql.entity;

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

    @Setter
    @Column(name = "spnd_spnd_id")
    private UUID spendingId;

    @Column(name = "prnt_prnt_id")
    private UUID participantId;

    @Column(name = "proportion")
    private BigDecimal proportion;

    @Column(name = "amount")
    private BigDecimal amount;
}
