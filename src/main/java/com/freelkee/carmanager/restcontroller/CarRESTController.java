package com.freelkee.carmanager.restcontroller;

import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Owner;
import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/car")
public class CarRESTController {
    private final CarService carService;

    @Autowired
    public CarRESTController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getCars() {
        return carService.getCars();
    }

    @GetMapping("/{id}/seller")
    public List<Seller> getSellers(@PathVariable final Long id){
        return carService.getSellers(id);
    }
    @GetMapping("/{id}/owner")
    public List<Owner> getOwners(@PathVariable final Long id){
        return carService.getOwners(id);
    }

}
