package com.freelkee.carmanager.service;

import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.repository.CarRepository;
import com.freelkee.carmanager.repository.SellerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {

    private final CarRepository carRepository;
    private final SellerRepository sellerRepository;


    public SellerService(
            final CarRepository carRepository,
            final SellerRepository sellerRepository
    ) {
        this.carRepository = carRepository;
        this.sellerRepository = sellerRepository;
    }

    public List<Seller> getSellers() {
        return sellerRepository.findAll();
    }

    public List<Car> getCars(final Long id) {
        return carRepository.findAllBySeller(id);
    }

    public Seller getSeller(final Long id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Seller %d does not exist", id)));
    }
}
