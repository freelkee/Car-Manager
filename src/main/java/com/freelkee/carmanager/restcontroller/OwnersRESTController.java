package com.freelkee.carmanager.restcontroller;

import com.freelkee.carmanager.response.OwnerResponse;
import com.freelkee.carmanager.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/owner")
public class OwnersRESTController {
    private final OwnerService ownerService;

    public OwnersRESTController(final OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    @Operation(summary = "Getting a list of \"OwnerResponse\" consisting of all owners in the database.")
    public List<OwnerResponse> getOwners() {
        return ownerService.getOwners();
    }

    @GetMapping("/without-car")
    @Operation(summary = "Getting a list of \"OwnerResponse\" consisting of" +
        " all owners in the database who do not have a car")
    public Set<OwnerResponse> getOwnersWithoutCar() {
        return ownerService.getOwnersWithoutCar();
    }

}
