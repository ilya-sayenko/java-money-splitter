package org.example.moneysplitter.party.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.moneysplitter.party.dao.postgresql.repository.*;
import org.example.moneysplitter.party.model.Party;
import org.example.moneysplitter.party.model.PartyParticipant;
import org.example.moneysplitter.party.model.PartySpending;
import org.example.moneysplitter.party.model.PartyTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class PartyServiceImplTest {
    private final PartyServiceImpl partyService;
    private final PartyRepository partyRepository;
    private final ParticipantRepository participantRepository;
    private final SpendingRepository spendingRepository;
    private final ProportionRepository proportionRepository;
    private final TransactionRepository transactionRepository;
    private Party party;
    private PartyParticipant participant;

    @BeforeEach
    public void setUp() {
        party = Party.builder()
                .name("test party")
                .description("test party")
                .totalAmount(BigDecimal.ZERO)
                .build();

        participant = PartyParticipant
                .builder()
                .name("test participant")
                .build();
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
        proportionRepository.deleteAll();
        spendingRepository.deleteAll();
        participantRepository.deleteAll();
        partyRepository.deleteAll();
    }

    @Test
    void shouldSaveParty() {
        Party savedParty = partyService.saveParty(party);

        assertNotNull(savedParty.getId());
        assertEquals(party.getName(), savedParty.getName());
        assertEquals(party.getDescription(), savedParty.getDescription());
        assertEquals(party.getTotalAmount(), savedParty.getTotalAmount());
    }

    @Test
    void shouldFindPartyById() {
        Party savedParty = partyService.saveParty(party);
        Party foundParty = partyService.findPartyById(savedParty.getId());

        assertEquals(savedParty.getId(), foundParty.getId());
        assertEquals(savedParty.getName(), foundParty.getName());
        assertEquals(savedParty.getDescription(), foundParty.getDescription());
        assertEquals(savedParty.getTotalAmount(), foundParty.getTotalAmount());
    }

    @Test
    void shouldSaveParticipant() {
        Party savedParty = partyService.saveParty(party);
        PartyParticipant savedParticipant = partyService.saveParticipant(participant.withPartyId(savedParty.getId()));

        assertNotNull(savedParticipant.getId());
        assertEquals(savedParticipant.getName(), participant.getName());
        assertEquals(savedParticipant.getPartyId(), savedParty.getId());
    }

    @Test
    void shouldSaveEqualSpending() {
        Party savedParty = partyService.saveParty(party);
        UUID partyId = savedParty.getId();
        PartyParticipant participantOne = partyService.saveParticipant(participant.withPartyId(partyId));
        PartyParticipant participantTwo = partyService.saveParticipant(participant.withPartyId(partyId));
        PartySpending spending = PartySpending
                .builder()
                .partyId(partyId)
                .payerId(participantOne.getId())
                .name("Name")
                .amount(BigDecimal.valueOf(100))
                .splitType(PartySpending.SplitType.EQUAL)
                .build();
        PartySpending savedSpending = partyService.saveSpending(spending);

        assertEquals(partyId, savedSpending.getPartyId());
        assertEquals(participantOne.getId(), savedSpending.getPayerId());
        assertEquals(spending.getName(), savedSpending.getName());
        assertEquals(0, BigDecimal.valueOf(100).compareTo(savedSpending.getAmount()));
        assertEquals(0, BigDecimal.valueOf(50).compareTo(savedSpending.getProportions().get(participantOne.getId()).getAmount()));
        assertEquals(0, BigDecimal.valueOf(50).compareTo(savedSpending.getProportions().get(participantTwo.getId()).getAmount()));
    }

    @Test
    void shouldSaveAmountSpending() {
        Party savedParty = partyService.saveParty(party);
        UUID partyId = savedParty.getId();
        PartyParticipant participantOne = partyService.saveParticipant(participant.withPartyId(partyId));
        PartyParticipant participantTwo = partyService.saveParticipant(participant.withPartyId(partyId));
        UUID participantOneId = participantOne.getId();
        UUID participantTwoId = participantTwo.getId();
        PartySpending spending = PartySpending
                .builder()
                .partyId(partyId)
                .payerId(participantOneId)
                .name("Name")
                .splitType(PartySpending.SplitType.AMOUNT)
                .proportions(Map.of(
                        participantOneId, PartySpending.Portion.builder().portion(BigDecimal.ONE).amount(BigDecimal.valueOf(10)).build(),
                        participantTwoId, PartySpending.Portion.builder().portion(BigDecimal.ONE).amount(BigDecimal.valueOf(90)).build()
                ))
                .build();
        PartySpending savedSpending = partyService.saveSpending(spending);

        assertEquals(partyId, savedSpending.getPartyId());
        assertEquals(participantOneId, savedSpending.getPayerId());
        assertEquals(spending.getName(), savedSpending.getName());
        assertEquals(0, BigDecimal.valueOf(100).compareTo(savedSpending.getAmount()));
        assertEquals(0, BigDecimal.valueOf(10).compareTo(savedSpending.getProportions().get(participantOneId).getAmount()));
        assertEquals(0, BigDecimal.valueOf(90).compareTo(savedSpending.getProportions().get(participantTwoId).getAmount()));
    }

    @Test
    void shouldSavePartitionSpending() {
        Party savedParty = partyService.saveParty(party);
        UUID partyId = savedParty.getId();
        PartyParticipant participantOne = partyService.saveParticipant(participant.withPartyId(partyId));
        PartyParticipant participantTwo = partyService.saveParticipant(participant.withPartyId(partyId));
        UUID participantOneId = participantOne.getId();
        UUID participantTwoId = participantTwo.getId();
        PartySpending spending = PartySpending
                .builder()
                .partyId(partyId)
                .payerId(participantOneId)
                .name("Name")
                .amount(BigDecimal.valueOf(100))
                .splitType(PartySpending.SplitType.PARTITION)
                .proportions(Map.of(
                        participantOneId, PartySpending.Portion.builder().portion(BigDecimal.valueOf(1)).amount(BigDecimal.ZERO).build(),
                        participantTwoId, PartySpending.Portion.builder().portion(BigDecimal.valueOf(9)).amount(BigDecimal.ZERO).build()
                ))
                .build();
        PartySpending savedSpending = partyService.saveSpending(spending);

        assertEquals(partyId, savedSpending.getPartyId());
        assertEquals(participantOneId, savedSpending.getPayerId());
        assertEquals(spending.getName(), savedSpending.getName());
        assertEquals(0, BigDecimal.valueOf(100).compareTo(savedSpending.getAmount()));
        assertEquals(0, BigDecimal.valueOf(10).compareTo(savedSpending.getProportions().get(participantOneId).getAmount()));
        assertEquals(0, BigDecimal.valueOf(90).compareTo(savedSpending.getProportions().get(participantTwoId).getAmount()));
    }

    @Test
    void shouldCalculateTransactions() {
        Party savedParty = partyService.saveParty(party);
        UUID partyId = savedParty.getId();
        PartyParticipant participantOne = partyService.saveParticipant(participant.withPartyId(partyId));
        PartyParticipant participantTwo = partyService.saveParticipant(participant.withPartyId(partyId));
        PartyParticipant participantThree = partyService.saveParticipant(participant.withPartyId(partyId));
        PartyParticipant participantFour = partyService.saveParticipant(participant.withPartyId(partyId));
        UUID participantOneId = participantOne.getId();
        UUID participantTwoId = participantTwo.getId();
        UUID participantThreeId = participantThree.getId();
        UUID participantFourId = participantFour.getId();

        partyService.saveSpending(
                PartySpending.builder()
                        .partyId(partyId)
                        .payerId(participantOneId)
                        .name("Milk")
                        .amount(BigDecimal.valueOf(800))
                        .splitType(PartySpending.SplitType.EQUAL)
                        .build()
        );

        partyService.saveSpending(
                PartySpending.builder()
                        .partyId(partyId)
                        .payerId(participantTwoId)
                        .name("Fruits")
                        .splitType(PartySpending.SplitType.AMOUNT)
                        .proportions(Map.of(
                                participantOneId, PartySpending.Portion.builder().amount(BigDecimal.valueOf(100)).build(),
                                participantThreeId, PartySpending.Portion.builder().amount(BigDecimal.valueOf(200)).build()
                        ))
                        .build()
        );

        partyService.saveSpending(
                PartySpending.builder()
                        .partyId(partyId)
                        .payerId(participantThreeId)
                        .name("Vegetables")
                        .splitType(PartySpending.SplitType.AMOUNT)
                        .proportions(Map.of(
                                participantTwoId, PartySpending.Portion.builder().amount(BigDecimal.valueOf(400)).build(),
                                participantFourId, PartySpending.Portion.builder().amount(BigDecimal.valueOf(200)).build()
                        ))
                        .build()
        );

        partyService.saveSpending(
                PartySpending.builder()
                        .partyId(partyId)
                        .payerId(participantFourId)
                        .name("Meat")
                        .splitType(PartySpending.SplitType.AMOUNT)
                        .proportions(Map.of(
                                participantOneId, PartySpending.Portion.builder().amount(BigDecimal.valueOf(500)).build()
                        ))
                        .build()
        );

        partyService.saveSpending(
                PartySpending.builder()
                        .partyId(partyId)
                        .payerId(participantTwoId)
                        .name("Drinks")
                        .splitType(PartySpending.SplitType.AMOUNT)
                        .proportions(Map.of(
                                participantThreeId, PartySpending.Portion.builder().amount(BigDecimal.valueOf(300)).build(),
                                participantFourId, PartySpending.Portion.builder().amount(BigDecimal.valueOf(300)).build()
                        ))
                        .build()
        );
        List<PartyTransaction> transactions = partyService.findTransactionsByPartyId(partyId);
        PartyTransaction transactionFromThreeToTwo = transactions
                .stream()
                .filter(t -> t.getPayerId().equals(participantThreeId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Wrong transaction"));
        PartyTransaction transactionFromFourToTwo = transactions
                .stream()
                .filter(t -> t.getPayerId().equals(participantFourId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Wrong transaction"));

        assertEquals(participantThreeId, transactionFromThreeToTwo.getPayerId());
        assertEquals(participantTwoId, transactionFromThreeToTwo.getPayeeId());
        assertEquals(0, BigDecimal.valueOf(100).compareTo(transactionFromThreeToTwo.getAmount()));
        assertEquals(participantFourId, transactionFromFourToTwo.getPayerId());
        assertEquals(participantTwoId, transactionFromFourToTwo.getPayeeId());
        assertEquals(0, BigDecimal.valueOf(200).compareTo(transactionFromFourToTwo.getAmount()));
    }

    @Test
    void shouldChangePartyAmount() {
        Party savedParty = partyService.saveParty(party);
        UUID partyId = savedParty.getId();
        PartyParticipant participantOne = partyService.saveParticipant(participant.withPartyId(partyId));
        UUID participantOneId = participantOne.getId();
        assertEquals(BigDecimal.ZERO, savedParty.getTotalAmount());
        PartySpending savedSpending = partyService.saveSpending(
                PartySpending.builder()
                        .partyId(partyId)
                        .payerId(participantOneId)
                        .name("Milk")
                        .amount(BigDecimal.valueOf(800))
                        .splitType(PartySpending.SplitType.EQUAL)
                        .build()
        );
        assertEquals(BigDecimal.valueOf(800), partyService.findPartyById(partyId).getTotalAmount());
        partyService.deleteSpending(savedSpending.getPartyId(), savedSpending.getId());
        assertEquals(BigDecimal.ZERO, partyService.findPartyById(partyId).getTotalAmount());
    }
}