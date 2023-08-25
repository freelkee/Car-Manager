package com.freelkee.carmanager.controller;

import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Owner;
import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.repository.CarRepository;
import com.freelkee.carmanager.response.CarResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class CarControllerTest {

    private final CarRepository carRepository;

    private final MockMvc mockMvc;

    @Autowired
    public CarControllerTest(CarRepository carRepository, MockMvc mockMvc) {
        this.carRepository = carRepository;
        this.mockMvc = mockMvc;
    }

    @Test
    public void showAllCars() throws Exception {
        var car1 = new Car();
        car1.setPrice(25000);
        car1.setYear(2022);
        car1.setEnginePower(200);

        var car2 = new Car();
        car2.setPrice(18000);
        car2.setYear(2021);
        car2.setEnginePower(180);

        carRepository.save(car1);
        carRepository.save(car2);

        var sortedCars = Stream.of(car1, car2)
            .sorted((c1, c2) -> Math.toIntExact(c1.getId() - c2.getId()))
            .collect(Collectors.toList());

        var result = mockMvc.perform(get("/car"))
            .andExpect(status().isOk())
            .andExpect(view().name("cars"))
            .andExpect(model().attributeExists("cars"))
            .andReturn();

        var modelMap = Objects.requireNonNull(result.getModelAndView()).getModelMap();

        var sortedCarsResponses = ((List<CarResponse>) modelMap.get("cars")).stream()
            .sorted((c1, c2) -> Math.toIntExact(c1.getId() - c2.getId()))
            .collect(Collectors.toList());

        assertNotNull(sortedCarsResponses);
        assertEquals(sortedCars.size(), sortedCarsResponses.size());

        for (int i = 0; i < sortedCars.size(); i++) {
            assertEquals(sortedCars.get(i).getId(), sortedCarsResponses.get(i).getId());
            assertEquals(sortedCars.get(i).getYear(), sortedCarsResponses.get(i).getYear());
            assertEquals(sortedCars.get(i).getPrice(), sortedCarsResponses.get(i).getPrice());
            assertEquals(
                sortedCars.get(i).getEnginePower(),
                sortedCarsResponses.get(i).getEnginePower()
            );
        }

    }

    @Test
    public void showSellersAndOwners() throws Exception {
        var seller1 = new Seller();
        seller1.setName("Big Shop");

        var seller2 = new Seller();
        seller2.setName("Small Shop");

        var sellers = List.of(seller1, seller2);

        var owner1 = new Owner();
        owner1.setName("Jack");

        var owner2 = new Owner();
        owner2.setName("Piter");

        var owners = List.of(owner1, owner2);

        var car = new Car();
        car.setPrice(25000);
        car.setYear(2022);
        car.setEnginePower(200);
        car.setSellers(Set.of(seller1, seller2));
        car.setOwners(Set.of(owner1, owner2));

        carRepository.save(car);

        var sortedSellers = sellers.stream()
            .sorted((s1, s2) -> Math.toIntExact(s1.getId() - s2.getId()))
            .collect(Collectors.toList());

        var sortedOwners = owners.stream()
            .sorted((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()))
            .collect(Collectors.toList());

        var result = mockMvc.perform(get("/car/" + car.getId()))
            .andExpect(status().isOk())
            .andExpect(view().name("car"))
            .andExpect(model().attributeExists("sellers", "owners", "id"))
            .andReturn();

        var modelMap = Objects.requireNonNull(result.getModelAndView()).getModelMap();

        var sortedSellersResponses = ((Set<Seller>) modelMap.get("sellers")).stream()
            .sorted((s1, s2) -> Math.toIntExact(s1.getId() - s2.getId()))
            .collect(Collectors.toList());

        assertNotNull(sortedSellersResponses);
        assertEquals(sellers.size(), sortedSellersResponses.size());

        for (int i = 0; i < sellers.size(); i++) {
            assertEquals(sortedSellers.get(i).getId(), sortedSellersResponses.get(i).getId());
            assertEquals(sortedSellers.get(i).getName(), sortedSellersResponses.get(i).getName());
        }

        var sortedOwnersResponses = ((Set<Owner>) modelMap.get("owners")).stream()
            .sorted((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()))
            .collect(Collectors.toList());

        assertNotNull(sortedSellersResponses);
        assertEquals(owners.size(), sortedOwnersResponses.size());

        for (int i = 0; i < owners.size(); i++) {
            assertEquals(sortedOwners.get(i).getId(), sortedOwnersResponses.get(i).getId());
            assertEquals(sortedOwners.get(i).getName(), sortedOwnersResponses.get(i).getName());
        }

        var returnedCarsId = (Long) modelMap.get("id");
        assertNotNull(returnedCarsId);
        assertEquals(car.getId(), returnedCarsId);
    }
}