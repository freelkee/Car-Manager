package com.freelkee.carmanager.restcontroller;

import com.freelkee.carmanager.entity.Owner;
import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.service.CarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

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

    @GetMapping("/{id}/seller")
    public Set<Seller> getSellers(@PathVariable final Long id) {
        return carService.getSellers(id);
    }

    @GetMapping("/{id}/owner")
    public Set<Owner> getOwners(@PathVariable final Long id) {
        return carService.getOwners(id);
    }

}
