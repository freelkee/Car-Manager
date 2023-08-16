package com.freelkee.carmanager.repository;

import com.freelkee.carmanager.entity.Car;
import com.freelkee.carmanager.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    List<Owner> findAllByCar(Car car);
}
