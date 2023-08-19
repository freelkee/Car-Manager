package com.freelkee.carmanager.repository;

import com.freelkee.carmanager.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    //    @Query("SELECT c FROM Car c JOIN c.sellers s WHERE s.id = :sellerId")
    //    List<Car> findAllBySeller(Long sellerId);

    @Query(value = "SELECT * FROM cars c " +
            "JOIN availability a " +
            "ON a.car_id = c.id " +
            "WHERE a.seller_id = :sellerId",
            nativeQuery = true)
    List<Car> findAllBySeller(@Param("sellerId")Long sellerId);

}
