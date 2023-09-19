package com.freelkee.carmanager.restcontroller;

import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.response.SellerResponse;
import com.freelkee.carmanager.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Getting a list of \"SellerResponse\" consisting of all sellers in the database.")
    public List<SellerResponse> getSellers() {
        return sellerService.getSellers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Getting a list of \"CarResponse\" consisting of all the cars that the seller sells.")
    public List<CarResponse> getCars(@PathVariable final Long id) {
        return sellerService.getCars(id);
    }
}
