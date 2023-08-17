package com.freelkee.carmanager.service;

import com.freelkee.carmanager.entity.Availability;
import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.entity.Owner;
import com.freelkee.carmanager.repository.AvailabilityRepository;
import com.freelkee.carmanager.repository.CarRepository;
import com.freelkee.carmanager.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;
    private final AvailabilityRepository availabilityRepository;
    @Autowired
    public CarService(CarRepository carRepository, OwnerRepository ownerRepository, AvailabilityRepository availabilityRepository) {
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
        this.availabilityRepository = availabilityRepository;
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public List<Owner> getOwners(Long id) {
        Car car = carRepository.findById(id).get();
        return ownerRepository.findAllByCar(car);
    }

    public List<Seller> getSellers(Long id) {
        Car car = carRepository.findById(id).get();
        List<Seller> sellers = new ArrayList<>();
        List<Availability> availability = availabilityRepository.findAllByCar(car);
        availability.forEach(a -> sellers.add(a.getSeller()));
        return sellers;
    }
}
