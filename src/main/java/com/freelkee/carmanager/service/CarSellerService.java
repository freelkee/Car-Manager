package com.freelkee.carmanager.service;

import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.CarSeller;

import java.util.List;


public interface CarSellerService {
    List<CarSeller> getCarSellers();
    List<Car> getCars(Long id);

}
