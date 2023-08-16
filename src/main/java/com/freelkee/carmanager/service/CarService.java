package com.freelkee.carmanager.service;

import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.CarSeller;
import com.freelkee.carmanager.entity.Owner;

import java.util.List;

public interface CarService {
    List<Car> getCars();
    List<Owner> getOwners(Long id);
    List<CarSeller> getCarSellers(Long id);

}
