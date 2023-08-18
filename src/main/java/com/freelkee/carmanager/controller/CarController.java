package com.freelkee.carmanager.controller;

import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Owner;
import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public String getCars(Model model) {
        List<Car> cars = carService.getCars();
        model.addAttribute("cars", cars);
        return "cars";
    }

    @GetMapping("/car/{id}")
    public String getSellers(@PathVariable final Long id, Model model){
        List<Seller> sellers = carService.getSellers(id);
        List<Owner> owners = carService.getOwners(id);

        model.addAttribute("sellers", sellers)
                .addAttribute("owners", owners)
                .addAttribute("id", id);
        return "car";
    }

}
