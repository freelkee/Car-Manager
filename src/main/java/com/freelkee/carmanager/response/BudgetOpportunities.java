package com.freelkee.carmanager.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BudgetOpportunities {

    private final List<CarBudgetAvailability> carBudgetAvailabilities;


}
