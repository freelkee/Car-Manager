package com.freelkee.carmanager.service;

import com.freelkee.carmanager.entity.Availability;
import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.CarSeller;
import com.freelkee.carmanager.entity.Owner;
import com.freelkee.carmanager.repository.AvailabilityRepository;
import com.freelkee.carmanager.repository.CarRepository;
import com.freelkee.carmanager.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;
    private final AvailabilityRepository availabilityRepository;
    @Autowired
    public CarServiceImpl(CarRepository carRepository, OwnerRepository ownerRepository, AvailabilityRepository availabilityRepository) {
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    @Override
    public List<Owner> getOwners(Long id) {
        Car car = carRepository.findById(id).get();
        return ownerRepository.findAllByCar(car);
    }

    @Override
    public List<CarSeller> getCarSellers(Long id) {
        Car car = carRepository.findById(id).get();
        List<CarSeller> carSellers = new ArrayList<>();
        List<Availability> availability = availabilityRepository.findAllByCar(car);
        availability.stream().filter(a -> a.getCar().equals(car)).forEach(a -> carSellers.add(a.getCarSeller()));
        return carSellers;
    }
}
