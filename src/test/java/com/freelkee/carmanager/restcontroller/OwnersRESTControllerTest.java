package com.freelkee.carmanager.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freelkee.carmanager.BaseTestContainersTest;
import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Owner;
import com.freelkee.carmanager.repository.OwnerRepository;
import com.freelkee.carmanager.response.OwnerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class OwnersRESTControllerTest extends BaseTestContainersTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getOwnersWithoutCar() throws Exception {

        final var owner1 = new Owner();
        owner1.setName("Owner 1");

        final var owner2 = new Owner();
        owner2.setName("Owner 2");

        final var car = new Car();
        car.setYear(2023);
        car.setPrice(20000);
        car.setEnginePower(180);

        final var owner3 = new Owner();
        owner3.setName("Owner 3");
        owner3.setCar(car);

        car.setOwners(Set.of(owner3));

        final var ownersWithoutCar = Arrays.asList(owner1, owner2, owner3);

        ownerRepository.saveAll(ownersWithoutCar);

        final var expectedOwnersResponse = ownersWithoutCar.stream()
            .map(OwnerResponse::of).filter(o -> o.getCarId() == null).collect(Collectors.toList());

        var result = mockMvc.perform(get("/api/v1/owner/without-car"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(expectedOwnersResponse.size())))
            .andExpect(jsonPath("$[0].carId", equalTo(null)))
            .andExpect(jsonPath("$[1].carId", equalTo(null)))
            .andReturn();

        final var stringResult = result.getResponse().getContentAsString();
        List<LinkedHashMap> actualOwnersInLinkedHashMaps = new ObjectMapper().readValue(stringResult, List.class);

        for (int i = 0; i < expectedOwnersResponse.size(); i++) {
            var actual = actualOwnersInLinkedHashMaps.get(i);
            var expected = expectedOwnersResponse.get(i);
            assertNull(actual.get("carId"));
            assertEquals(actual.get("id"), expected.getId().intValue());
            assertEquals(actual.get("name"), expected.getName());
            assertEquals(actual.get("budget"), expected.getBudget());
        }

    }
}