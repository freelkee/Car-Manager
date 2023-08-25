package com.freelkee.carmanager.controller;

import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Owner;
import com.freelkee.carmanager.repository.OwnerRepository;
import com.freelkee.carmanager.response.OwnerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class OwnerControllerTest {

    private final OwnerRepository ownerRepository;

    private final MockMvc mockMvc;

    @Autowired
    public OwnerControllerTest(OwnerRepository ownerRepository, MockMvc mockMvc) {
        this.ownerRepository = ownerRepository;
        this.mockMvc = mockMvc;
    }

    @Test
    public void showAllOwners() throws Exception {
        var owner1 = new Owner();
        owner1.setName("Jack");

        var owner2 = new Owner();
        owner2.setName("Piter");

        var owners = Set.of(owner1,owner2);

        var car = new Car();
        car.setPrice(25000);
        car.setYear(2022);
        car.setEnginePower(200);
        car.setOwners(owners);

        owner1.setCar(car);
        owner2.setCar(car);

        ownerRepository.save(owner1);
        ownerRepository.save(owner2);

        var sortedOwners = owners.stream()
            .sorted((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()))
            .collect(Collectors.toList());

        var result = mockMvc.perform(get("/owner"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners"))
            .andExpect(model().attributeExists("owners"))
            .andReturn();

        var modelMap = Objects.requireNonNull(result.getModelAndView()).getModelMap();
        var sortedOwnersResponses = ((List<OwnerResponse>) modelMap.get("owners")).stream()
            .sorted((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()))
            .collect(Collectors.toList());

        assertEquals(owners.size(), sortedOwnersResponses.size());

        assertEquals(sortedOwners.get(0).getId(), sortedOwnersResponses.get(0).getId());
        assertEquals(sortedOwners.get(0).getName(), sortedOwnersResponses.get(0).getName());
        assertEquals(sortedOwners.get(0).getCar().getId(), sortedOwnersResponses.get(0).getCarId());

        assertEquals(sortedOwners.get(1).getId(), sortedOwnersResponses.get(1).getId());
        assertEquals(sortedOwners.get(1).getName(), sortedOwnersResponses.get(1).getName());
        assertEquals(sortedOwners.get(1).getCar().getId(), sortedOwnersResponses.get(1).getCarId());

    }
}