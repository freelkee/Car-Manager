package com.freelkee.carmanager.repository;


import com.freelkee.carmanager.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    List<Seller> findAll();
    Optional<Seller> findById(Long id);

}
