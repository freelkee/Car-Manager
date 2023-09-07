package com.freelkee.carmanager.repository;

import com.freelkee.carmanager.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> getAllByYearBetweenOrderByYearAsc(int year, int year2);
    List<Car> getAllByPriceBetweenOrderByPriceAsc(int year, int year2);
    @Query("SELECT c FROM Car c WHERE c.price <= (SELECT o.budget FROM Owner o WHERE o.id = :ownerId)")
    List<Car> findCarsByOwnerBudget(@Param("ownerId") Long ownerId);
}
