package com.freelkee.carmanager.restcontroller;

import com.freelkee.carmanager.response.BudgetOpportunities;
import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/car")
public class CarRESTController {
    private final CarService carService;

    public CarRESTController(final CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    @Operation(summary = "Getting a list of \"CarResponse\" consisting of all cars in the database.")
    public List<CarResponse> getCars() {
        return carService.getCars();
    }

    @GetMapping("/search")
    @Operation(summary = "Getting a list of \"CarResponse\" consisting of all cars in the " +
        "database included in the specified range by year of manufacture and price.")
    public List<CarResponse> searchCars(
        @RequestParam(name = "minYear") final int minYear,
        @RequestParam(name = "maxYear") final int maxYear,
        @RequestParam(name = "minPrice") final int minPrice,
        @RequestParam(name = "maxPrice") final int maxPrice
    ) {
        return carService.searchCars(minYear, maxYear, minPrice, maxPrice);
    }

    @GetMapping("/budget-opportunities/{ownerId}")
    @Operation(summary = "Getting a \"BudgetOpportunities\" consisting of all cars in the " +
        "database whose price is less than or equal to the owner's budget with a given ID")
    public BudgetOpportunities getBudgetOpportunities(@PathVariable final Long ownerId) {
        return carService.getCarsByOwnerId(ownerId);
    }
}
