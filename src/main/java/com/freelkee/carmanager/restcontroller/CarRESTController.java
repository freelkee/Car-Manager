package com.freelkee.carmanager.restcontroller;

import com.freelkee.carmanager.response.BudgetOpportunities;
import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.service.CarService;
import com.freelkee.carmanager.service.OwnerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public BudgetOpportunities getBudgetOpportunities(@PathVariable final Long ownerId) {
        var carBudgetAvailability = carService.getCarsByOwnerBudget(ownerService.getOwner(ownerId).getBudget());
        return new BudgetOpportunities(carBudgetAvailability);

    }
}
