package com.freelkee.carmanager.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BudgetOpportunities {
    private final OwnerResponse ownerResponse;
    private final List<CarResponse> carResponses;

}
