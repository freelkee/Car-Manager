package com.freelkee.carmanager.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freelkee.carmanager.BaseTestContainersTest;
import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Owner;
import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.repository.CarRepository;
import com.freelkee.carmanager.repository.OwnerRepository;
import com.freelkee.carmanager.response.BudgetOpportunities;
import com.freelkee.carmanager.response.CarBudgetAvailability;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class CarRESTControllerTest extends BaseTestContainersTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void searchCars() throws Exception {

        final var car1 = Car.builder()
            .year(2021)
            .price(20000)
            .enginePower(120)
            .build();

        final var car2 = Car.builder()
            .year(2021)
            .price(25000)
            .enginePower(140)
            .build();

        final var car3 = Car.builder()
            .year(2022)
            .price(30000)
            .enginePower(160)
            .build();

        final var car4 = Car.builder()
            .year(2022)
            .price(35000)
            .enginePower(180)
            .build();

        final var cars = Arrays.asList(car1, car2, car3, car4);
        carRepository.saveAll(cars);

        final int minYear = 25000;
        final int maxYear = 30000;
        final int minPrice = 2021;
        final int maxPrice = 2022;

        final var expectedCar = cars.stream()
            .filter
                (
                    c -> c.getPrice() >= minPrice &&
                        c.getPrice() <= maxPrice &&
                        c.getYear() >= minYear &&
                        c.getYear() <= maxYear
                )
            .collect(Collectors.toList());

        var result = mockMvc.perform(get(String.format(
                "/api/v1/car/search?minYear=%d&maxYear=%d&minPrice=%d&maxPrice=%d",
                minYear, maxYear, minPrice, maxPrice)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(expectedCar.size())))
            .andReturn();

        final var stringResult = result.getResponse().getContentAsString();
        List<LinkedHashMap<String, Integer>> actualCarsInLinkedHashMaps = new ObjectMapper().readValue(stringResult, List.class);

        for (int i = 0; i < expectedCar.size(); i++) {
            var actual = actualCarsInLinkedHashMaps.get(i);
            assertTrue
                (
                    actual.get("price") >= minPrice &&
                        actual.get("price") <= maxPrice &&
                        actual.get("year") >= minYear &&
                        actual.get("year") <= maxYear
                );
        }
    }

    @Test
    void getBudgetOpportunities() throws Exception {
        final var seller1 = Seller.builder()
            .name("Big Shop")
            .build();

        final var seller2 = Seller.builder()
            .name("Small Shop")
            .build();

        final var car1 = Car.builder()
            .year(2021)
            .price(20000)
            .enginePower(120)
            .sellers(Set.of(seller1))
            .build();

        final var car2 = Car.builder()
            .year(2021)
            .price(25000)
            .enginePower(140)
            .sellers(Set.of(seller2))
            .build();

        final var car3 = Car.builder()
            .year(2022)
            .price(30000)
            .enginePower(160)
            .build();

        final var owner = Owner.builder()
            .name("Owner")
            .budget(27000)
            .build();

        final var cars = Arrays.asList(car1, car2, car3);

        carRepository.saveAll(cars);
        ownerRepository.save(owner);

        Long ownerId = owner.getId();

        var result = mockMvc.perform(get("/api/v1/car/budget-opportunities/" + ownerId))
            .andExpect(status().isOk())
            .andReturn();

        final var stringResult = result.getResponse().getContentAsString();
        BudgetOpportunities response = new ObjectMapper().readValue(stringResult, BudgetOpportunities.class);

        for (CarBudgetAvailability carBudgetAvailability : response.getCarBudgetAvailabilities()) {
            assertTrue(carBudgetAvailability.getCarResponse().getPrice() <= owner.getBudget());
        }

    }
}