package com.moneysplitter.mapper.proportion;

import com.moneysplitter.controller.data.SpendingCreateRequest;
import com.moneysplitter.controller.data.SplitType;
import com.moneysplitter.dao.ParticipantDao;
import com.moneysplitter.model.PartyParticipant;
import com.moneysplitter.model.SpendingProportion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.moneysplitter.controller.data.SplitType.EQUAL;

@Component
@RequiredArgsConstructor
public class ProportionCalculatorEqual implements ProportionCalculator {

    private static final int SCALE = 6;

    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    private final ParticipantDao participantDao;

    @Override
    public List<SpendingProportion> calculate(SpendingCreateRequest request) {
        List<PartyParticipant> participants = participantDao.findParticipantsByPartyId(request.partyId());
        BigDecimal value = request.amount().divide(BigDecimal.valueOf(participants.size()), SCALE, ROUNDING_MODE);

        return participants.stream()
                .map(participant -> {
                    return SpendingProportion.builder()
                            .participant(PartyParticipant.builder().id(participant.getId()).build())
                            .proportion(BigDecimal.ONE)
                            .amount(value)
                            .build();
                })
                .toList();
    }

    @Override
    public SplitType getSplitType() {
        return EQUAL;
    }
}
