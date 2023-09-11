package com.freelkee.carmanager.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BudgetOpportunities {

    private List<CarBudgetAvailability> carBudgetAvailabilities;

}
