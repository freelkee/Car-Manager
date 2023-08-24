package com.freelkee.carmanager.controller;

import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Owner;
import com.freelkee.carmanager.repository.OwnerRepository;
import com.freelkee.carmanager.response.OwnerResponse;
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
public class OwnerControllerTest {

    @MockBean
    private OwnerRepository ownerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void showAllOwners() throws Exception {
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
        car.setOwners(Set.of(owner1, owner2));

        owner1.setCar(car);
        owner2.setCar(car);

        List<Owner> owners = List.of(owner1,owner2);

        Mockito.when(ownerRepository.findAll()).thenReturn(owners);

        MvcResult result = mockMvc.perform(get("/owner"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners"))
            .andExpect(model().attributeExists("owners"))
            .andReturn();

        ModelMap modelMap = Objects.requireNonNull(result.getModelAndView()).getModelMap();
        List<OwnerResponse> returnedOwners = (List<OwnerResponse>) modelMap.get("owners");
        assertNotNull(returnedOwners);
        assertEquals(owners.size(), returnedOwners.size());

        assertEquals(OwnerResponse.of(owner1), returnedOwners.get(0));
        assertEquals(OwnerResponse.of(owner2), returnedOwners.get(1));

    }
}