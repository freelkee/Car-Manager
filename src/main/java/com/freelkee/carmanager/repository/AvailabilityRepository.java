package com.freelkee.carmanager.repository;

import com.freelkee.carmanager.entity.Availability;
import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    List<Availability> findAllBySeller(Seller seller);
    List<Availability> findAllByCar(Car car);
}
