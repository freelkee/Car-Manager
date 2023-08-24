package com.freelkee.carmanager.controller;

import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.repository.SellerRepository;
import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.response.SellerResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SellerControllerTest {

    @MockBean
    private SellerRepository sellerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void showAllSellers() throws Exception {
        Seller seller1 = new Seller();
        seller1.setId(1L);
        seller1.setName("Big Shop");

        Seller seller2 = new Seller();
        seller2.setId(2L);
        seller2.setName("Small Shop");

        Car car1 = new Car();
        car1.setId(1L);
        car1.setPrice(25000);
        car1.setYear(2022);
        car1.setEnginePower(200);

        Car car2 = new Car();
        car1.setId(2L);
        car2.setPrice(18000);
        car2.setYear(2021);
        car2.setEnginePower(180);

        seller1.setCars(Set.of(car1));
        seller2.setCars(Set.of(car1,car2));

        List<Seller> sellers = List.of(seller1,seller2);

        Mockito.when(sellerRepository.findAll()).thenReturn(sellers);

        MvcResult result = mockMvc.perform(get("/seller"))
            .andExpect(status().isOk())
            .andExpect(view().name("sellers"))
            .andExpect(model().attributeExists("sellers"))
            .andReturn();

        ModelMap modelMap = Objects.requireNonNull(result.getModelAndView()).getModelMap();

        List<SellerResponse> returnedSellers = (List<SellerResponse>) modelMap.get("sellers");
        assertNotNull(returnedSellers);
        assertEquals(sellers.size(), returnedSellers.size());

        assertEquals(SellerResponse.of(seller1), returnedSellers.get(0));
        assertEquals(SellerResponse.of(seller2), returnedSellers.get(1));

    }

    @Test
    public void showSeller() throws Exception {
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setName("Big Shop");

        Car car1 = new Car();
        car1.setId(1L);
        car1.setPrice(25000);
        car1.setYear(2022);
        car1.setEnginePower(200);
        car1.setSellers(Set.of(seller));

        Car car2 = new Car();
        car1.setId(2L);
        car2.setPrice(18000);
        car2.setYear(2021);
        car2.setEnginePower(180);
        car2.setSellers(Set.of(seller));

        seller.setCars(Set.of(car1));

        Mockito.when(sellerRepository.getReferenceById(seller.getId())).thenReturn(seller);
        Mockito.when(sellerRepository.findById(seller.getId())).thenReturn(Optional.of(seller));

        mockMvc.perform(get("/seller/" + seller.getId()))
            .andExpect(status().isOk())
            .andExpect(view().name("seller"))
            .andExpect(model().attributeExists("seller", "cars"))
            .andExpect(model().attribute("seller", SellerResponse.of(seller)))
            .andExpect(model().attribute("cars", seller.getCars()
                .stream()
                .map(CarResponse::of)
                .collect(Collectors.toList())));
    }
}