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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.ui.ModelMap;

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
@Sql(scripts = "/schema-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CarControllerTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void showAllCars() throws Exception {
        final var car1 = Car.builder()
            .price(25000)
            .year(2022)
            .enginePower(200)
            .build();

        final var car2 = Car.builder()
            .price(18000)
            .year(2021)
            .enginePower(180)
            .build();

        carRepository.save(car1);
        carRepository.save(car2);

        final var sortedCars = Stream.of(car1, car2)
            .sorted((c1, c2) -> Math.toIntExact(c1.getId() - c2.getId()))
            .collect(Collectors.toList());

        final List<CarResponse> sortedCarsResponses = getCarResponses();

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

    private List<CarResponse> getCarResponses() throws Exception {
        final ModelMap modelMap = getModelMap("/car", "cars", model().attributeExists("cars"));

        return ((List<CarResponse>) modelMap.get("cars")).stream()
            .sorted((c1, c2) -> Math.toIntExact(c1.getId() - c2.getId()))
            .collect(Collectors.toList());
    }

    @Test
    public void showSellersAndOwners() throws Exception {
        final var seller1 = Seller.builder()
            .name("Big Shop")
            .build();

        final var seller2 = Seller.builder()
            .name("Small Shop")
            .build();

        final var sellers = List.of(seller1, seller2);

        final var owner1 = Owner.builder()
            .name("Jack")
            .build();

        final var owner2 = Owner.builder()
            .name("Piter")
            .build();

        final var owners = List.of(owner1, owner2);

        final var car = Car.builder()
            .price(25000)
            .year(2022)
            .enginePower(200)
            .sellers(Set.of(seller1, seller2))
            .owners(Set.of(owner1, owner2))
            .build();

        carRepository.save(car);

        final var sortedSellers = sellers.stream()
            .sorted((s1, s2) -> Math.toIntExact(s1.getId() - s2.getId()))
            .collect(Collectors.toList());

        final var sortedOwners = owners.stream()
            .sorted((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()))
            .collect(Collectors.toList());

        final ModelMap modelMap = getModelMap(
            "/car/" + car.getId(),
            "car",
            model().attributeExists("sellers", "owners", "id")
        );

        final var sortedSellersResponses = ((Set<Seller>) modelMap.get("sellers")).stream()
            .sorted((s1, s2) -> Math.toIntExact(s1.getId() - s2.getId()))
            .collect(Collectors.toList());

        assertSellers(sortedSellers, sortedSellersResponses);

        final var sortedOwnersResponses = ((Set<Owner>) modelMap.get("owners")).stream()
            .sorted((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()))
            .collect(Collectors.toList());

        assertOwners(sortedOwners, sortedOwnersResponses);

        final var returnedCarsId = (Long) modelMap.get("id");
        assertNotNull(returnedCarsId);
        assertEquals(car.getId(), returnedCarsId);
    }

    private static void assertOwners(List<Owner> sortedOwners, List<Owner> sortedOwnersResponses) {
        assertNotNull(sortedOwnersResponses);
        assertEquals(sortedOwners.size(), sortedOwnersResponses.size());

        for (int i = 0; i < sortedOwners.size(); i++) {
            assertEquals(sortedOwners.get(i).getId(), sortedOwnersResponses.get(i).getId());
            assertEquals(sortedOwners.get(i).getName(), sortedOwnersResponses.get(i).getName());
        }
    }

    private static void assertSellers(List<Seller> sortedSellers, List<Seller> sortedSellersResponses) {
        assertNotNull(sortedSellersResponses);
        assertEquals(sortedSellers.size(), sortedSellersResponses.size());

        for (int i = 0; i < sortedSellers.size(); i++) {
            assertEquals(sortedSellers.get(i).getId(), sortedSellersResponses.get(i).getId());
            assertEquals(sortedSellers.get(i).getName(), sortedSellersResponses.get(i).getName());
        }
    }

    private ModelMap getModelMap(String car, String car1, ResultMatcher matcher) throws Exception {
        final var result = mockMvc.perform(get(car))
            .andExpect(status().isOk())
            .andExpect(view().name(car1))
            .andExpect(matcher)
            .andReturn();

        return Objects.requireNonNull(result.getModelAndView()).getModelMap();
    }
}