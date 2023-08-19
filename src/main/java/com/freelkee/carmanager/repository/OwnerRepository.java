package com.freelkee.carmanager.repository;

import com.freelkee.carmanager.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    List<Owner> findAllByCarId(Long car_id);

}
