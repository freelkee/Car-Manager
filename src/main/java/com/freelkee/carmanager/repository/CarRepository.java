package com.freelkee.carmanager.repository;

import com.freelkee.carmanager.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> getByPriceLessThanEqual(int budget);

    List<Car> getAllByYearBetweenAndPriceBetweenOrderByYearAscPriceAsc(int minYear, int maxYear, int minPrice, int maxPrice);

}
