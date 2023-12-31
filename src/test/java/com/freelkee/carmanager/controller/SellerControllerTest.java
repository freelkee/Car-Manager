package com.freelkee.carmanager.controller;

import com.freelkee.carmanager.BaseTestContainersTest;
import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.repository.SellerRepository;
import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.response.SellerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
public class SellerControllerTest extends BaseTestContainersTest {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void showAllSellers() throws Exception {
        final var seller1 = Seller.builder()
            .name("Big Shop")
            .address("Уфа")
            .build();

        final var seller2 = Seller.builder()
            .name("Small Shop")
            .address("Северодвинск")
            .build();

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

        seller1.setCars(Set.of(car2));
        seller2.setCars(Set.of(car1));

        sellerRepository.save(seller1);
        sellerRepository.save(seller2);

        final var sortedSellers = Stream.of(seller1, seller2)
            .sorted((s1, s2) -> Math.toIntExact(s1.getId() - s2.getId()))
            .collect(Collectors.toList());

        final var result = mockMvc.perform(get("/seller"))
            .andExpect(status().isOk())
            .andExpect(view().name("sellers"))
            .andExpect(model().attributeExists("sellers"))
            .andReturn();

        final var modelMap = Objects.requireNonNull(result.getModelAndView()).getModelMap();

        final var sortedSellersResponses = ((List<SellerResponse>) modelMap.get("sellers")).stream()
            .sorted((s1, s2) -> Math.toIntExact(s1.getId() - s2.getId()))
            .collect(Collectors.toList());

        assertSellers(sortedSellers, sortedSellersResponses);
    }

    private static void assertSellers(List<Seller> sortedSellers, List<SellerResponse> sortedSellersResponses) {
        assertNotNull(sortedSellersResponses);
        assertEquals(sortedSellers.size(), sortedSellersResponses.size());

        for (int i = 0; i < sortedSellers.size(); i++) {
            final Seller expected = sortedSellers.get(i);
            final SellerResponse actual = sortedSellersResponses.get(i);

            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getAddress(), extractCityName(actual.getAddress()));
        }
    }

    @Test
    public void showSeller() throws Exception {
        final var seller = Seller.builder()
            .name("Big Shop")
            .build();

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

        seller.setCars(Set.of(car1, car2));

        sellerRepository.save(seller);

        final var sortedCars = (new ArrayList<>(seller.getCars())).stream()
            .sorted((c1, c2) -> Math.toIntExact(c1.getId() - c2.getId()))
            .collect(Collectors.toList());

        final var result = mockMvc.perform(get("/seller/" + seller.getId()))
            .andExpect(status().isOk())
            .andExpect(view().name("seller"))
            .andExpect(model().attributeExists("seller", "cars"))
            .andReturn();

        final var modelMap = Objects.requireNonNull(result.getModelAndView()).getModelMap();

        final var returnedSellerResponse = (SellerResponse) modelMap.get("seller");

        assertSeller(seller, returnedSellerResponse);

        final var sortedCarsResponses = ((List<CarResponse>) modelMap.get("cars")).stream()
            .sorted((c1, c2) -> Math.toIntExact(c1.getId() - c2.getId()))
            .collect(Collectors.toList());

        assertCars(sortedCars, sortedCarsResponses);
    }

    private static void assertCars(List<Car> sortedCars, List<CarResponse> sortedCarsResponses) {
        assertNotNull(sortedCarsResponses);
        assertEquals(sortedCars.size(), sortedCarsResponses.size());

        for (int i = 0; i < sortedCars.size(); i++) {
            final var car = sortedCars.get(i);
            final var carResponse = sortedCarsResponses.get(i);

            assertEquals(car.getId(), carResponse.getId());
            assertEquals(car.getPrice(), carResponse.getPrice());
            assertEquals(car.getEnginePower(), carResponse.getEnginePower());
        }
    }

    private static void assertSeller(Seller seller, SellerResponse returnedSellerResponse) {
        assertNotNull(returnedSellerResponse);

        assertEquals(seller.getId(), returnedSellerResponse.getId());
        assertEquals(seller.getName(), returnedSellerResponse.getName());
    }

    private static String extractCityName(String responseAddress) {
        final String regex = "^(\\p{L}+).*";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(responseAddress);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            throw new RuntimeException("Could not extract information from the string.");
        }
    }
}