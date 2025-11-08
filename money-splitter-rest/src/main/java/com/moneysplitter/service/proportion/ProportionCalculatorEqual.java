package com.moneysplitter.service.proportion;

import com.moneysplitter.dao.ParticipantDao;
import com.moneysplitter.model.PartyParticipant;
import com.moneysplitter.model.PartySpending;
import com.moneysplitter.model.SpendingPortion;
import com.moneysplitter.model.SplitType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProportionCalculatorEqual implements ProportionCalculator {

    private static final int SCALE = 6;

    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    private final ParticipantDao participantDao;

    @Override
    public Map<UUID, SpendingPortion> calculate(PartySpending spending) {
        List<PartyParticipant> participants = participantDao.findParticipantsByPartyId(spending.getPartyId());
        BigDecimal value = spending.getAmount().divide(BigDecimal.valueOf(participants.size()), SCALE, ROUNDING_MODE);

        return participants
                .stream()
                .collect(Collectors.toMap(
                        PartyParticipant::getId,
                        p -> SpendingPortion.builder().amount(value).build()));
    }

    @Override
    public SplitType getSplitType() {
        return SplitType.EQUAL;
    }
}
