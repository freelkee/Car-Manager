package com.freelkee.carmanager.service;

import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.entity.Owner;
import com.freelkee.carmanager.repository.CarRepository;
import com.freelkee.carmanager.repository.OwnerRepository;
import com.freelkee.carmanager.repository.SellerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    private final OwnerRepository ownerRepository;

    private final SellerRepository sellerRepository;

    public CarService(
            final CarRepository carRepository,
            final OwnerRepository ownerRepository,
            final SellerRepository sellerRepository
    ) {
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
        this.sellerRepository = sellerRepository;
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public List<Owner> getOwners(final Long id) {
        return ownerRepository.findAllByCarId(id);
    }

    public List<Seller> getSellers(final Long id) {
        return sellerRepository.findAllByCar(id);

    }
}
