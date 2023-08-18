package com.freelkee.carmanager.controller;
import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.service.SellerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Controller
public class SellerController {
    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping("/sellers")
    public String getSellers(Model model){
        List<Seller> sellers = sellerService.getSellers();
        model.addAttribute("sellers", sellers);
        return "sellers";
    }

    @GetMapping("/seller/{id}")
    public String getSeller(@PathVariable final Long id, Model model){
        model.addAttribute("seller", sellerService.getSeller(id));
        model.addAttribute("cars", sellerService.getCars(id));

        return "seller";

    }
}
