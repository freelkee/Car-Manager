package com.freelkee.carmanager.repository;

import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Set<Owner> getAllByCar(Car car);

}
