package com.moneysplitter.core.data;

import lombok.Builder;
import com.moneysplitter.core.model.Spending;

import java.util.List;

@Builder
public record InputData(

        List<String> participants,

        List<Spending> spendings
) {
}
