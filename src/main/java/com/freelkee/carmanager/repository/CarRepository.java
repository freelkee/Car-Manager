package com.freelkee.carmanager.repository;

import com.freelkee.carmanager.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> getAllByYearBetweenOrderByYearAsc(int year, int year2);
    List<Car> getAllByPriceBetweenOrderByPriceAsc(int year, int year2);
}
