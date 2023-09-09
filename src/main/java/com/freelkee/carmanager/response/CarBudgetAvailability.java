package com.freelkee.carmanager.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CarBudgetAvailability {

    private final CarResponse carResponse;

    private final List<SellerResponse> sellerResponses;

}
