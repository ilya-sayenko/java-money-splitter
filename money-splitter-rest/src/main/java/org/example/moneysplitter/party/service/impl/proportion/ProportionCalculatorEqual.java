package org.example.moneysplitter.party.service.impl.proportion;

import lombok.RequiredArgsConstructor;
import org.example.moneysplitter.party.dao.PartyDao;
import org.example.moneysplitter.party.model.PartyParticipant;
import org.example.moneysplitter.party.model.PartySpending;
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
    private final PartyDao partyDao;

    @Override
    public Map<UUID, PartySpending.Portion> calculate(PartySpending spending) {
        List<PartyParticipant> participants = partyDao.findParticipantsByPartyId(spending.getPartyId());
        BigDecimal value = spending.getAmount().divide(BigDecimal.valueOf(participants.size()), SCALE, ROUNDING_MODE);

        return participants
                .stream()
                .collect(Collectors.toMap(
                        PartyParticipant::getId,
                        p -> PartySpending.Portion.builder().amount(value).build()));
    }

    @Override
    public PartySpending.SplitType getSplitType() {
        return PartySpending.SplitType.EQUAL;
    }
}
