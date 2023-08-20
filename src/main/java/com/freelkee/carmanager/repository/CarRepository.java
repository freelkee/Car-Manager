package com.freelkee.carmanager.repository;

import com.freelkee.carmanager.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

}
