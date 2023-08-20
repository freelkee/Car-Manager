package com.freelkee.carmanager.controller;

import com.freelkee.carmanager.service.SellerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(final SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping()
    public String getSellers(final Model model) {
        model.addAttribute("sellers", sellerService.getSellers());
        return "sellers";
    }

    @GetMapping("/{id}")
    public String getSeller(@PathVariable final Long id, final Model model) {
        model.addAttribute("seller", sellerService.getSeller(id))
            .addAttribute("cars", sellerService.getCars(id));
        return "seller";
    }
}
