package com.freelkee.carmanager.restcontroller;

import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.service.CarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/car")
public class CarRESTController {
    private final CarService carService;

    public CarRESTController(final CarService carService) {
        this.carService = carService;
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

    @GetMapping("/budget/{ownerId}")
    public List<Car> getSellersByBudget(@PathVariable final Long ownerId) {
        return carService.getCarsByOwnerBudget(ownerId);
    }
}
