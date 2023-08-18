package com.freelkee.carmanager.restcontroller;
import com.freelkee.carmanager.entity.Owner;
import com.freelkee.carmanager.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owner")
public class OwnersRESTController {
    private final OwnerService ownerService;
    @Autowired
    public OwnersRESTController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public List<Owner> getOwners(){
        return ownerService.getOwners();
    }
}
