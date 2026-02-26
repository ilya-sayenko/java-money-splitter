package com.moneysplitter.service;

import com.moneysplitter.dao.postgresql.entity.PartyEntity;
import com.moneysplitter.dao.postgresql.repository.ParticipantRepository;
import com.moneysplitter.dao.postgresql.repository.PartyRepository;
import com.moneysplitter.dao.postgresql.repository.ProportionRepository;
import com.moneysplitter.dao.postgresql.repository.SpendingRepository;
import com.moneysplitter.dao.postgresql.repository.TransactionRepository;
import com.moneysplitter.model.Party;
import com.moneysplitter.model.PartyParticipant;
import com.moneysplitter.model.PartySpending;
import com.moneysplitter.model.PartyTransaction;
import com.moneysplitter.model.SpendingProportion;
import com.moneysplitter.model.SplitType;
import lombok.RequiredArgsConstructor;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SplitterServiceImplTest {

    private final SplitterService splitterService;

    private final PartyRepository partyRepository;

    private final ParticipantRepository participantRepository;

    private final SpendingRepository spendingRepository;

    private final ProportionRepository proportionRepository;

    private final TransactionRepository transactionRepository;

    @AfterEach
    public void tearDown() {
        transactionRepository.deleteAll();
        proportionRepository.deleteAll();
        spendingRepository.deleteAll();
        participantRepository.deleteAll();
        partyRepository.deleteAll();
    }

    @Test
    public void shouldCreateParty() {
        Party party = Instancio.of(Party.class)
                .ignore(field(Party::getId))
                .create();
        UUID partyId = splitterService.createParty(party);
        Optional<PartyEntity> savedPartyOptional = partyRepository.findById(partyId);

        assertTrue(savedPartyOptional.isPresent());

        PartyEntity savedParty = savedPartyOptional.get();

        assertEquals(party.getName(), savedParty.getName());
        assertEquals(party.getDescription(), savedParty.getDescription());
    }

    @Test
    public void shouldFindPartyById() {
        Party party = Instancio.create(Party.class);
        UUID savedPartyId = splitterService.createParty(party);
        Party foundParty = splitterService.findPartyById(savedPartyId);

        assertEquals(party.getName(), foundParty.getName());
        assertEquals(party.getDescription(), foundParty.getDescription());
    }
/*
    @Test
    void shouldSaveParticipant() {
        Party savedParty = splitterService.createParty(party);
        PartyParticipant savedParticipant = splitterService.createParticipant(participant.withPartyId(savedParty.getId()));

        assertNotNull(savedParticipant.getId());
        assertEquals(savedParticipant.getName(), participant.getName());
        assertEquals(savedParticipant.getPartyId(), savedParty.getId());
    }

    @Test
    void shouldSaveEqualSpending() {
        Party savedParty = splitterService.createParty(party);
        UUID partyId = savedParty.getId();
        PartyParticipant participantOne = splitterService.createParticipant(participant.withPartyId(partyId));
        PartyParticipant participantTwo = splitterService.createParticipant(participant.withPartyId(partyId));
        PartySpending spending = PartySpending
                .builder()
                .partyId(partyId)
                .payerId(participantOne.getId())
                .name("Name")
                .amount(BigDecimal.valueOf(100))
                .splitType(SplitType.EQUAL)
                .build();
        PartySpending savedSpending = splitterService.createSpending(spending);

        assertEquals(partyId, savedSpending.getPartyId());
        assertEquals(participantOne.getId(), savedSpending.getPayerId());
        assertEquals(spending.getName(), savedSpending.getName());
        assertEquals(0, BigDecimal.valueOf(100).compareTo(savedSpending.getAmount()));
        assertEquals(0, BigDecimal.valueOf(50).compareTo(savedSpending.getProportions().get(participantOne.getId()).getAmount()));
        assertEquals(0, BigDecimal.valueOf(50).compareTo(savedSpending.getProportions().get(participantTwo.getId()).getAmount()));
    }

    @Test
    void shouldSaveAmountSpending() {
        Party savedParty = splitterService.createParty(party);
        UUID partyId = savedParty.getId();
        PartyParticipant participantOne = splitterService.createParticipant(participant.withPartyId(partyId));
        PartyParticipant participantTwo = splitterService.createParticipant(participant.withPartyId(partyId));
        UUID participantOneId = participantOne.getId();
        UUID participantTwoId = participantTwo.getId();
        PartySpending spending = PartySpending
                .builder()
                .partyId(partyId)
                .payerId(participantOneId)
                .name("Name")
                .splitType(SplitType.AMOUNT)
                .proportions(Map.of(
                        participantOneId, PartySpending.Portion.builder().portion(BigDecimal.ONE).amount(BigDecimal.valueOf(10)).build(),
                        participantTwoId, PartySpending.Portion.builder().portion(BigDecimal.ONE).amount(BigDecimal.valueOf(90)).build()
                ))
                .build();
        PartySpending savedSpending = splitterService.createSpending(spending);

        assertEquals(partyId, savedSpending.getPartyId());
        assertEquals(participantOneId, savedSpending.getPayerId());
        assertEquals(spending.getName(), savedSpending.getName());
        assertEquals(0, BigDecimal.valueOf(100).compareTo(savedSpending.getAmount()));
        assertEquals(0, BigDecimal.valueOf(10).compareTo(savedSpending.getProportions().get(participantOneId).getAmount()));
        assertEquals(0, BigDecimal.valueOf(90).compareTo(savedSpending.getProportions().get(participantTwoId).getAmount()));
    }

    @Test
    void shouldSavePartitionSpending() {
        Party savedParty = splitterService.createParty(party);
        UUID partyId = savedParty.getId();
        PartyParticipant participantOne = splitterService.createParticipant(participant.withPartyId(partyId));
        PartyParticipant participantTwo = splitterService.createParticipant(participant.withPartyId(partyId));
        UUID participantOneId = participantOne.getId();
        UUID participantTwoId = participantTwo.getId();
        PartySpending spending = PartySpending
                .builder()
                .partyId(partyId)
                .payerId(participantOneId)
                .name("Name")
                .amount(BigDecimal.valueOf(100))
                .splitType(SplitType.PARTITION)
                .proportions(Map.of(
                        participantOneId, PartySpending.Portion.builder().portion(BigDecimal.valueOf(1)).amount(BigDecimal.ZERO).build(),
                        participantTwoId, PartySpending.Portion.builder().portion(BigDecimal.valueOf(9)).amount(BigDecimal.ZERO).build()
                ))
                .build();
        PartySpending savedSpending = splitterService.createSpending(spending);

        assertEquals(partyId, savedSpending.getPartyId());
        assertEquals(participantOneId, savedSpending.getPayerId());
        assertEquals(spending.getName(), savedSpending.getName());
        assertEquals(0, BigDecimal.valueOf(100).compareTo(savedSpending.getAmount()));
        assertEquals(0, BigDecimal.valueOf(10).compareTo(savedSpending.getProportions().get(participantOneId).getAmount()));
        assertEquals(0, BigDecimal.valueOf(90).compareTo(savedSpending.getProportions().get(participantTwoId).getAmount()));
    }
*/
    @Test
    public void shouldCalculateTransactions() {
        Party party = Instancio.of(Party.class)
                .ignore(field(Party::getId))
                .create();
        UUID partyId = splitterService.createParty(party);
        UUID participantOneId = splitterService.createParticipant(
                Instancio.of(PartyParticipant.class)
                        .ignore(field(PartyParticipant::getId))
                        .set(field(PartyParticipant::getPartyId), partyId)
                        .create()
        );
        UUID participantTwoId = splitterService.createParticipant(
                Instancio.of(PartyParticipant.class)
                        .ignore(field(PartyParticipant::getId))
                        .set(field(PartyParticipant::getPartyId), partyId)
                        .create()
        );
        UUID participantThreeId = splitterService.createParticipant(
                Instancio.of(PartyParticipant.class)
                        .ignore(field(PartyParticipant::getId))
                        .set(field(PartyParticipant::getPartyId), partyId)
                        .create()
        );
        UUID participantFourId = splitterService.createParticipant(
                Instancio.of(PartyParticipant.class)
                        .ignore(field(PartyParticipant::getId))
                        .set(field(PartyParticipant::getPartyId), partyId)
                        .create()
        );

        splitterService.createSpending(
                PartySpending.builder()
                        .partyId(partyId)
                        .payer(PartyParticipant.builder().id(participantOneId).build())
                        .name("Milk")
                        .amount(BigDecimal.valueOf(800))
                        .splitType(SplitType.EQUAL)
                        .proportions(List.of(
                                SpendingProportion.builder()
                                        .proportion(BigDecimal.ONE)
                                        .amount(BigDecimal.valueOf(200))
                                        .participant(PartyParticipant.builder().id(participantOneId).build())
                                        .build(),
                                SpendingProportion.builder()
                                        .proportion(BigDecimal.ONE)
                                        .amount(BigDecimal.valueOf(200))
                                        .participant(PartyParticipant.builder().id(participantTwoId).build())
                                        .build(),
                                SpendingProportion.builder()
                                        .proportion(BigDecimal.ONE)
                                        .amount(BigDecimal.valueOf(200))
                                        .participant(PartyParticipant.builder().id(participantThreeId).build())
                                        .build(),
                                SpendingProportion.builder()
                                        .proportion(BigDecimal.ONE)
                                        .amount(BigDecimal.valueOf(200))
                                        .participant(PartyParticipant.builder().id(participantFourId).build())
                                        .build()
                        ))
                        .build()
        );

        splitterService.createSpending(
                PartySpending.builder()
                        .partyId(partyId)
                        .payer(PartyParticipant.builder().id(participantTwoId).build())
                        .name("Fruits")
                        .splitType(SplitType.AMOUNT)
                        .proportions(List.of(
                                SpendingProportion.builder()
                                        .proportion(BigDecimal.ONE)
                                        .amount(BigDecimal.valueOf(100))
                                        .participant(PartyParticipant.builder().id(participantOneId).build())
                                        .build(),
                                SpendingProportion.builder()
                                        .proportion(BigDecimal.ONE)
                                        .amount(BigDecimal.valueOf(200))
                                        .participant(PartyParticipant.builder().id(participantThreeId).build())
                                        .build()
                        ))
                        .build()
        );

        splitterService.createSpending(
                PartySpending.builder()
                        .partyId(partyId)
                        .payer(PartyParticipant.builder().id(participantThreeId).build())
                        .name("Vegetables")
                        .splitType(SplitType.AMOUNT)
                        .proportions(List.of(
                                SpendingProportion.builder()
                                        .proportion(BigDecimal.ONE)
                                        .amount(BigDecimal.valueOf(400))
                                        .participant(PartyParticipant.builder().id(participantTwoId).build())
                                        .build(),
                                SpendingProportion.builder()
                                        .proportion(BigDecimal.ONE)
                                        .amount(BigDecimal.valueOf(200))
                                        .participant(PartyParticipant.builder().id(participantFourId).build())
                                        .build()
                        ))
                        .build()
        );

        splitterService.createSpending(
                PartySpending.builder()
                        .partyId(partyId)
                        .payer(PartyParticipant.builder().id(participantFourId).build())
                        .name("Meat")
                        .splitType(SplitType.AMOUNT)
                        .proportions(List.of(
                                SpendingProportion.builder()
                                        .proportion(BigDecimal.ONE)
                                        .amount(BigDecimal.valueOf(500))
                                        .participant(PartyParticipant.builder().id(participantOneId).build())
                                        .build()
                        ))
                        .build()
        );

        splitterService.createSpending(
                PartySpending.builder()
                        .partyId(partyId)
                        .payer(PartyParticipant.builder().id(participantTwoId).build())
                        .name("Drinks")
                        .splitType(SplitType.AMOUNT)
                        .proportions(List.of(
                                SpendingProportion.builder()
                                        .proportion(BigDecimal.ONE)
                                        .amount(BigDecimal.valueOf(300))
                                        .participant(PartyParticipant.builder().id(participantThreeId).build())
                                        .build(),
                                SpendingProportion.builder()
                                        .proportion(BigDecimal.ONE)
                                        .amount(BigDecimal.valueOf(300))
                                        .participant(PartyParticipant.builder().id(participantFourId).build())
                                        .build()
                        ))
                        .build()
        );
        List<PartyTransaction> transactions = splitterService.findTransactionsByPartyId(partyId);
        PartyTransaction transactionFromThreeToTwo = transactions
                .stream()
                .filter(t -> t.getPayer().getId().equals(participantThreeId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Wrong transaction"));
        PartyTransaction transactionFromFourToTwo = transactions
                .stream()
                .filter(t -> t.getPayer().getId().equals(participantFourId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Wrong transaction"));

        assertEquals(participantThreeId, transactionFromThreeToTwo.getPayer().getId());
        assertEquals(participantTwoId, transactionFromThreeToTwo.getPayee().getId());
        assertEquals(0, BigDecimal.valueOf(100).compareTo(transactionFromThreeToTwo.getAmount()));
        assertEquals(participantFourId, transactionFromFourToTwo.getPayer().getId());
        assertEquals(participantTwoId, transactionFromFourToTwo.getPayee().getId());
        assertEquals(0, BigDecimal.valueOf(200).compareTo(transactionFromFourToTwo.getAmount()));
    }

/*
    @Test
    public void shouldChangePartyAmount() {
        Party savedParty = splitterService.createParty(party);
        UUID partyId = savedParty.getId();
        PartyParticipant participantOne = splitterService.createParticipant(participant.withPartyId(partyId));
        UUID participantOneId = participantOne.getId();
        assertEquals(BigDecimal.ZERO, savedParty.getTotalAmount());
        PartySpending savedSpending = splitterService.createSpending(
                PartySpending.builder()
                        .partyId(partyId)
                        .payerId(participantOneId)
                        .name("Milk")
                        .amount(BigDecimal.valueOf(800))
                        .splitType(SplitType.EQUAL)
                        .build()
        );
        assertEquals(BigDecimal.valueOf(800), splitterService.findPartyById(partyId).getTotalAmount());
        splitterService.deleteSpendingById(savedSpending.getPartyId(), savedSpending.getId());
        assertEquals(BigDecimal.ZERO, splitterService.findPartyById(partyId).getTotalAmount());
    }*/
}