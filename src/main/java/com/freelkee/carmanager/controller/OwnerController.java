package com.freelkee.carmanager.controller;

import com.freelkee.carmanager.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/owner")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(final OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping()
    public String getOwners(final Model model) {
        model.addAttribute("owners", ownerService.getOwners());
        return "owners";
    }
}
