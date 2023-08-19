package com.freelkee.carmanager.restcontroller;

import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/seller")
public class SellerRESTController {
    private final SellerService sellerService;

    @GetMapping
    public List<Seller> getSellers() {
        return sellerService.getSellers();
    }

    @GetMapping("/{id}")
    public List<Car> getCars(@PathVariable final Long id) {
        return sellerService.getCars(id);
    }
}
