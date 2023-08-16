package com.freelkee.carmanager.repository;


import com.freelkee.carmanager.entity.CarSeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarSellerRepository extends JpaRepository<CarSeller, Long> {

}
