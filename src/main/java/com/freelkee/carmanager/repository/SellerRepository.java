package com.freelkee.carmanager.repository;

import com.freelkee.carmanager.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    @Query(value = "SELECT * FROM sellers s " +
            "JOIN availability a " +
            "ON a.seller_id = s.id " +
            "WHERE a.car_id = :carId",
            nativeQuery = true)
    List<Seller> findAllByCar(@Param("carId") Long carId);

}
