package com.freelkee.carmanager.controller;

import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Owner;
import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.repository.CarRepository;
import com.freelkee.carmanager.response.CarResponse;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTest {

    @MockBean
    private CarRepository carRepository;

    @Autowired
    private MockMvc mockMvc;



    @Test
    public void showAllCars() throws Exception {
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

        List<Car> cars = List.of(car1, car2);
        Mockito.when(carRepository.findAll()).thenReturn(cars);

        MvcResult result = mockMvc.perform(get("/car"))
            .andExpect(status().isOk())
            .andExpect(view().name("cars"))
            .andExpect(model().attributeExists("cars"))
            .andReturn();

        ModelMap modelMap = Objects.requireNonNull(result.getModelAndView()).getModelMap();

        List<CarResponse> returnedCars = (List<CarResponse>) modelMap.get("cars");
        assertNotNull(returnedCars);
        assertEquals(cars.size(), returnedCars.size());

        assertEquals(CarResponse.of(car1), returnedCars.get(0));
        assertEquals(CarResponse.of(car2), returnedCars.get(1));


    }

    @Test
    public void showSellersAndOwners() throws Exception {
        Seller seller1 = new Seller();
        seller1.setId(1L);
        seller1.setName("Big Shop");

        Seller seller2 = new Seller();
        seller2.setId(2L);
        seller2.setName("Small Shop");

        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setName("Jack");

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setName("Piter");

        Car car = new Car();
        car.setId(1L);
        car.setPrice(25000);
        car.setYear(2022);
        car.setEnginePower(200);
        car.setSellers(Set.of(seller1,seller2));
        car.setOwners(Set.of(owner1,owner2));

        Mockito.when(carRepository.getReferenceById(Mockito.any())).thenReturn(car);

        mockMvc.perform(get("/car/" + car.getId()))
            .andExpect(status().isOk())
            .andExpect(view().name("car"))
            .andExpect(model().attributeExists("sellers", "owners", "id"))
            .andExpect(model().attribute("sellers", car.getSellers()))
            .andExpect(model().attribute("owners", car.getOwners()))
            .andExpect(model().attribute("id", car.getId()))
            .andReturn();
    }
}