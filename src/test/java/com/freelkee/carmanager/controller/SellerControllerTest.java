package com.freelkee.carmanager.controller;

import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.repository.SellerRepository;
import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.response.SellerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class SellerControllerTest {

    private final SellerRepository sellerRepository;
    private final MockMvc mockMvc;

    @Autowired
    public SellerControllerTest(SellerRepository sellerRepository, MockMvc mockMvc) {
        this.sellerRepository = sellerRepository;
        this.mockMvc = mockMvc;
    }

    @Test
    public void showAllSellers() throws Exception {
        var seller1 = new Seller();
        seller1.setName("Big Shop");

        var seller2 = new Seller();
        seller2.setName("Small Shop");

        var car1 = new Car();
        car1.setPrice(25000);
        car1.setYear(2022);
        car1.setEnginePower(200);

        var car2 = new Car();
        car2.setPrice(18000);
        car2.setYear(2021);
        car2.setEnginePower(180);

        seller1.setCars(Set.of(car2));
        seller2.setCars(Set.of(car1));


        sellerRepository.save(seller1);
        sellerRepository.save(seller2);

        var sortedSellers = Stream.of(seller1, seller2)
            .sorted((s1, s2) -> Math.toIntExact(s1.getId() - s2.getId()))
            .collect(Collectors.toList());

        var result = mockMvc.perform(get("/seller"))
            .andExpect(status().isOk())
            .andExpect(view().name("sellers"))
            .andExpect(model().attributeExists("sellers"))
            .andReturn();

        var modelMap = Objects.requireNonNull(result.getModelAndView()).getModelMap();

        var sortedSellersResponses = ((List<SellerResponse>) modelMap.get("sellers")).stream()
            .sorted((s1, s2) -> Math.toIntExact(s1.getId() - s2.getId()))
            .collect(Collectors.toList());

        assertNotNull(sortedSellersResponses);
        assertEquals(sortedSellers.size(), sortedSellersResponses.size());

        assertEquals(sortedSellers.get(0).getId(), sortedSellersResponses.get(0).getId());
        assertEquals(sortedSellers.get(0).getName(), sortedSellersResponses.get(0).getName());

        assertEquals(sortedSellers.get(1).getId(), sortedSellersResponses.get(1).getId());
        assertEquals(sortedSellers.get(1).getName(), sortedSellersResponses.get(1).getName());

    }

    @Test
    public void showSeller() throws Exception {
        var seller = new Seller();
        seller.setName("Big Shop");

        var car1 = new Car();
        car1.setPrice(25000);
        car1.setYear(2022);
        car1.setEnginePower(200);

        var car2 = new Car();
        car2.setPrice(18000);
        car2.setYear(2021);
        car2.setEnginePower(180);

        seller.setCars(Set.of(car1, car2));

        sellerRepository.save(seller);

        var result = mockMvc.perform(get("/seller/" + seller.getId()))
            .andExpect(status().isOk())
            .andExpect(view().name("seller"))
            .andExpect(model().attributeExists("seller", "cars"))
            .andReturn();

        var modelMap = Objects.requireNonNull(result.getModelAndView()).getModelMap();

        var returnedSellerResponse = (SellerResponse) modelMap.get("seller");
        assertNotNull(returnedSellerResponse);

        assertEquals(seller.getId(), returnedSellerResponse.getId());
        assertEquals(seller.getName(), returnedSellerResponse.getName());

        var sortedCarsResponses = ((List<CarResponse>) modelMap.get("cars")).stream()
            .sorted((c1, c2) -> Math.toIntExact(c1.getId() - c2.getId()))
            .collect(Collectors.toList());

        assertNotNull(sortedCarsResponses);

        var sortedCars = (new ArrayList<>(seller.getCars())).stream()
            .sorted((c1, c2) -> Math.toIntExact(c1.getId() - c2.getId()))
            .collect(Collectors.toList());

        assertEquals(sortedCars.size(), sortedCarsResponses.size());

        for (int i = 0; i < sortedCars.size(); i++) {
            var car = sortedCars.get(i);
            var carResponse = sortedCarsResponses.get(i);

            assertEquals(car.getId(), carResponse.getId());
            assertEquals(car.getPrice(), carResponse.getPrice());
            assertEquals(car.getEnginePower(), carResponse.getEnginePower());
        }
    }
}