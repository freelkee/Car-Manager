package com.freelkee.carmanager.controller;

import com.freelkee.carmanager.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    public CarController(final CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public String getCars(final Model model) {
        model.addAttribute("cars", carService.getCars());
        return "cars";
    }

    @GetMapping("/{id}")
    public String getSellers(@PathVariable final Long id, final Model model) {
        model.addAttribute("sellers", carService.getSellers(id))
            .addAttribute("owners", carService.getOwners(id))
            .addAttribute("id", id);
        return "car";
    }

}
