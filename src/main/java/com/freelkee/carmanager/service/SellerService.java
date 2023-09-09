package com.freelkee.carmanager.service;

import com.freelkee.carmanager.repository.SellerRepository;
import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.response.SellerResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;


    public SellerService(final SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public List<SellerResponse> getSellers() {
        return sellerRepository.findAll().stream()
            .map(SellerResponse::of)
            .collect(Collectors.toList());
    }

    public List<CarResponse> getCars(final Long sellerId) {
        return sellerRepository.getReferenceById(sellerId).getCars()
            .stream()
            .map(CarResponse::of)
            .collect(Collectors.toList());
    }

    public SellerResponse getSeller(final Long id) {
        return SellerResponse.of(sellerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(String.format("Seller %d does not exist", id))));
    }

}
