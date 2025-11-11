package com.moneysplitter.mapper;

import com.moneysplitter.controller.data.ProportionResponse;
import com.moneysplitter.controller.data.SpendingCreateRequest;
import com.moneysplitter.dao.ParticipantDao;
import com.moneysplitter.dao.postgresql.entity.ProportionEntity;
import com.moneysplitter.mapper.proportion.ProportionCalculatorAmount;
import com.moneysplitter.mapper.proportion.ProportionCalculatorEqual;
import com.moneysplitter.mapper.proportion.ProportionCalculatorFactory;
import com.moneysplitter.mapper.proportion.ProportionCalculatorPartition;
import com.moneysplitter.model.PartyParticipant;
import com.moneysplitter.model.SpendingProportion;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.moneysplitter.controller.data.SplitType.AMOUNT;
import static com.moneysplitter.controller.data.SplitType.EQUAL;
import static com.moneysplitter.controller.data.SplitType.PARTITION;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ProportionMapperTest {

    private final ParticipantDao participantDao = Mockito.mock(ParticipantDao.class);

    private final ProportionMapper proportionMapper = Mappers.getMapper(ProportionMapper.class);

    private final ParticipantMapper participantMapper = Mappers.getMapper(ParticipantMapper.class);

    private final ProportionCalculatorFactory proportionCalculatorFactory = new ProportionCalculatorFactory(Set.of(
            new ProportionCalculatorAmount(),
            new ProportionCalculatorEqual(participantDao),
            new ProportionCalculatorPartition()
    ));

    @BeforeEach
    public void setUp() {
        proportionMapper.setParticipantMapper(participantMapper);
        proportionMapper.setProportionCalculatorFactory(proportionCalculatorFactory);
    }

    @Test
    public void shouldMapFromSplitAmountRequest() {
        Map<UUID, BigDecimal> proportionsRequest = Instancio.ofMap(UUID.class, BigDecimal.class).create();
        SpendingCreateRequest.Split splitRequest = SpendingCreateRequest.Split.builder()
                .splitType(AMOUNT)
                .participants(proportionsRequest)
                .build();
        SpendingCreateRequest request = Instancio.of(SpendingCreateRequest.class)
                .set(field(SpendingCreateRequest::split), splitRequest)
                .create();

        List<SpendingProportion> spendingProportions = proportionMapper.fromSplitRequest(request);

        assertEquals(request.split().participants().size(), spendingProportions.size());

        for (SpendingProportion spendingProportion : spendingProportions) {
            assertEquals(request.split().participants().get(spendingProportion.getParticipant().getId()), spendingProportion.getAmount());
            assertEquals(BigDecimal.ONE, spendingProportion.getProportion());
        }
    }

    @Test
    public void shouldMapFromSplitEqualRequest() {
        SpendingCreateRequest.Split splitRequest = SpendingCreateRequest.Split.builder()
                .splitType(EQUAL)
                .build();
        SpendingCreateRequest request = Instancio.of(SpendingCreateRequest.class)
                .set(field(SpendingCreateRequest::split), splitRequest)
                .create();
        List<PartyParticipant> partyParticipants = Instancio.ofList(PartyParticipant.class).create();
        when(participantDao.findParticipantsByPartyId(any(UUID.class))).thenReturn(partyParticipants);

        List<SpendingProportion> spendingProportions = proportionMapper.fromSplitRequest(request);
        int scale = 6;
        BigDecimal expectedAmount = request.amount().divide(BigDecimal.valueOf(partyParticipants.size()), scale, RoundingMode.HALF_EVEN);
        BigDecimal epsilon = BigDecimal.valueOf(0.00001);

        for (SpendingProportion spendingProportion : spendingProportions) {
            assertEquals(BigDecimal.ONE, spendingProportion.getProportion());
            assertTrue(spendingProportion.getAmount().subtract(expectedAmount).abs().compareTo(epsilon) < 0);
        }
    }

    @Test
    public void shouldMapFromSplitPartitionRequest() {
        Map<UUID, BigDecimal> proportionsRequest = Instancio.ofMap(UUID.class, BigDecimal.class)
                .size(10)
                .generate(Select.all(BigDecimal.class), gen -> gen.ints().range(1, 10).as(BigDecimal::valueOf))
                .create();
        SpendingCreateRequest.Split splitRequest = SpendingCreateRequest.Split.builder()
                .splitType(PARTITION)
                .participants(proportionsRequest).build();
        SpendingCreateRequest request = Instancio.of(SpendingCreateRequest.class)
                .set(field(SpendingCreateRequest::split), splitRequest)
                .create();

        List<SpendingProportion> spendingProportions = proportionMapper.fromSplitRequest(request);
        BigDecimal allProportions = request.split().participants().values()
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        int scale = 6;
        BigDecimal oneProportionAmount = request.amount().divide(allProportions, scale, RoundingMode.HALF_EVEN);
        BigDecimal epsilon = BigDecimal.valueOf(0.00001);

        for (SpendingProportion spendingProportion : spendingProportions) {
            assertEquals(request.split().participants().get(spendingProportion.getParticipant().getId()), spendingProportion.getProportion());
            assertTrue(oneProportionAmount.multiply(spendingProportion.getProportion())
                    .subtract(spendingProportion.getAmount())
                    .abs()
                    .compareTo(epsilon) < 0);
        }
    }

    @Test
    public void shouldMapToResponse() {
        SpendingProportion proportion = Instancio.create(SpendingProportion.class);
        ProportionResponse response = proportionMapper.toResponse(proportion);

        assertEquals(proportion.getProportion(), response.proportion());
        assertEquals(proportion.getAmount(), response.amount());
        assertEquals(proportion.getParticipant().getId(), response.participant().id());
        assertEquals(proportion.getParticipant().getName(), response.participant().name());
    }

    @Test
    public void shouldMapToEntity() {
        SpendingProportion proportion = Instancio.create(SpendingProportion.class);
        ProportionEntity entity = proportionMapper.toEntity(proportion);

        assertEquals(proportion.getId(), entity.getId());
        assertEquals(proportion.getSpendingId(), entity.getSpendingId());
        assertEquals(proportion.getProportion(), entity.getProportion());
        assertEquals(proportion.getAmount(), entity.getAmount());
        assertEquals(proportion.getParticipant().getId(), entity.getParticipant().getId());
        assertEquals(proportion.getParticipant().getName(), entity.getParticipant().getName());
    }

    @Test
    public void shouldMapFromEntity() {
        ProportionEntity entity = Instancio.create(ProportionEntity.class);
        SpendingProportion proportion = proportionMapper.fromEntity(entity);

        assertEquals(entity.getId(), proportion.getId());
        assertEquals(entity.getSpendingId(), proportion.getSpendingId());
        assertEquals(entity.getProportion(), proportion.getProportion());
        assertEquals(entity.getAmount(), proportion.getAmount());
        assertEquals(entity.getParticipant().getId(), proportion.getParticipant().getId());
        assertEquals(entity.getParticipant().getName(), proportion.getParticipant().getName());
    }
}