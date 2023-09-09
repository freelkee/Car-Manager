package com.freelkee.carmanager.restcontroller;

import com.freelkee.carmanager.response.*;
import com.freelkee.carmanager.service.CarService;
import com.freelkee.carmanager.service.OwnerService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public List<CarResponse> searchCars(
        @RequestParam(name = "minYear") final int minYear,
        @RequestParam(name = "maxYear") final int maxYear,
        @RequestParam(name = "minPrice") final int minPrice,
        @RequestParam(name = "maxPrice") final int maxPrice
    ) {
        return carService.searchCars(minYear, maxYear, minPrice, maxPrice);
    }

    @GetMapping("/budget-opportunities/{ownerId}")
    public BudgetOpportunities getSellersByBudget(@PathVariable final Long ownerId) {
        final var ownerResponse = OwnerResponse.of(ownerService.getOwner(ownerId));
        final var cars = carService.getCarsByOwnerBudget(ownerResponse.getBudget());

        final var carBudgetAvailabilities = cars.stream()
            .map(car -> new CarBudgetAvailability(
                CarResponse.of(car),
                car.getSellers().stream()
                    .map(SellerResponse::of)
                    .collect(Collectors.toList())
            ))
            .collect(Collectors.toList());

        return new BudgetOpportunities(carBudgetAvailabilities);
    }
}
