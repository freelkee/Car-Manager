package com.freelkee.carmanager.service;

import com.freelkee.carmanager.entity.Availability;
import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.repository.AvailabilityRepository;
import com.freelkee.carmanager.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final AvailabilityRepository availabilityRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository,
                         AvailabilityRepository availabilityRepository) {
        this.sellerRepository = sellerRepository;
        this.availabilityRepository = availabilityRepository;
    }

    public List<Seller> getSellers() {
        return sellerRepository.findAll();
    }

    public List<Car> getCars(Long id) {
        Seller seller = sellerRepository.findById(id).get();
        List<Car> cars = new ArrayList<>();
        List<Availability> availability = availabilityRepository.findAllBySeller(seller);
        availability.forEach(a -> cars.add(a.getCar()));
        return cars;
    }
}
