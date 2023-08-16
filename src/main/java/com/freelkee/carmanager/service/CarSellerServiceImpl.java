package com.freelkee.carmanager.service;

import com.freelkee.carmanager.entity.Availability;
import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.CarSeller;
import com.freelkee.carmanager.repository.AvailabilityRepository;
import com.freelkee.carmanager.repository.CarSellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarSellerServiceImpl implements CarSellerService {

    private final CarSellerRepository carSellerRepository;
    private final AvailabilityRepository availabilityRepository;

    @Autowired
    public CarSellerServiceImpl(CarSellerRepository carSellerRepository,
                                AvailabilityRepository availabilityRepository) {
        this.carSellerRepository = carSellerRepository;
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public List<CarSeller> getCarSellers() {
        return carSellerRepository.findAll();
    }

    @Override
    public List<Car> getCars(Long id) {
        CarSeller carSeller = carSellerRepository.findById(id).get();
        List<Car> cars = new ArrayList<>();
        List<Availability> availability = availabilityRepository.findAllByCarSeller(carSeller);
        availability.stream().filter(a -> a.getCarSeller().equals(carSeller)).forEach(a -> cars.add(a.getCar()));
        return cars;
    }
}
