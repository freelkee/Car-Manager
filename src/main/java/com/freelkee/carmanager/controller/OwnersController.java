package com.freelkee.carmanager.controller;
import com.freelkee.carmanager.entity.Owner;
import com.freelkee.carmanager.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OwnersController {
    private final OwnerService ownerService;
    @Autowired
    public OwnersController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/owners")
    public String getOwners(Model model){
        List<Owner> owners = ownerService.getOwners();
        model.addAttribute("owners", owners);
        return "owners";
    }
}
