package com.freelkee.carmanager.restcontroller;

import com.freelkee.carmanager.response.BudgetOpportunities;
import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.response.OwnerResponse;
import com.freelkee.carmanager.service.CarService;
import com.freelkee.carmanager.service.OwnerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/car")
public class CarRESTController {
    private final CarService carService;

    private final OwnerService ownerService;

    public CarRESTController(final CarService carService, final OwnerService ownerService) {
        this.carService = carService;
        this.ownerService = ownerService;
    }

    @GetMapping
    public List<CarResponse> getCars() {
        return carService.getCars();
    }

    @GetMapping("/by-year/between/{forr}-{to}")
    public List<CarResponse> getCarsBetweenYears(@PathVariable final int forr, @PathVariable final int to) {
        return carService.getCarsBetweenYears(forr, to);
    }

    @GetMapping("/by-price/between/{forr}-{to}")
    public List<CarResponse> getCarsBetweenPrice(@PathVariable final int forr, @PathVariable final int to) {
        return carService.getCarsBetweenPrice(forr, to);
    }

    @GetMapping("/budget-opportunities/{ownerId}")
    public BudgetOpportunities getSellersByBudget(@PathVariable final Long ownerId) {
        final var ownerResponse = OwnerResponse.of(ownerService.getOwner(ownerId));
        final var cars = carService.getCarsByOwnerBudget(ownerResponse.getBudget());

        return new BudgetOpportunities(
            ownerResponse,
            cars.stream()
                .map(CarResponse::of)
                .collect(Collectors.toList())
        );
    }
}
