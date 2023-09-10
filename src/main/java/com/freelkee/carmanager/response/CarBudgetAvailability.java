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
public class CarBudgetAvailability {

    private CarResponse carResponse;

    private List<SellerResponse> sellerResponses;

}
