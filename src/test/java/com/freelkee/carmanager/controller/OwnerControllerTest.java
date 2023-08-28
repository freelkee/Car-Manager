package com.freelkee.carmanager.controller;

import com.freelkee.carmanager.entity.Owner;
import com.freelkee.carmanager.repository.OwnerRepository;
import com.freelkee.carmanager.response.OwnerResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled("liquibase implementation")
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "/schema-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class OwnerControllerTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(scripts = "/owner-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void showAllOwners() throws Exception {

        final var owners = ownerRepository.findAll();

        final var sortedOwners = owners.stream()
            .sorted((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()))
            .collect(Collectors.toList());

        final var result = mockMvc.perform(get("/owner"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners"))
            .andExpect(model().attributeExists("owners"))
            .andReturn();

        final var modelMap = Objects.requireNonNull(result.getModelAndView()).getModelMap();
        final var sortedOwnersResponses = ((List<OwnerResponse>) modelMap.get("owners")).stream()
            .sorted((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()))
            .collect(Collectors.toList());

        assertOwners(sortedOwners, sortedOwnersResponses);
    }

    private static void assertOwners(List<Owner> sortedOwners, List<OwnerResponse> sortedOwnersResponses) {
        assertEquals(sortedOwners.size(), sortedOwnersResponses.size());

        for (int i = 0; i < sortedOwners.size(); i++) {
            assertEquals(sortedOwners.get(i).getId(), sortedOwnersResponses.get(i).getId());
            assertEquals(sortedOwners.get(i).getName(), sortedOwnersResponses.get(i).getName());
            assertEquals(sortedOwners.get(i).getCar().getId(), sortedOwnersResponses.get(i).getCarId());
        }
    }
}