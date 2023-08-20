package com.freelkee.carmanager.restcontroller;

import com.freelkee.carmanager.response.OwnerResponse;
import com.freelkee.carmanager.service.OwnerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owner")
public class OwnersRESTController {
    private final OwnerService ownerService;

    public OwnersRESTController(final OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public List<OwnerResponse> getOwners() {
        return ownerService.getOwners();
    }
}
