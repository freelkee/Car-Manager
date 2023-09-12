package com.freelkee.carmanager.service;

import com.freelkee.carmanager.entity.Owner;
import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.repository.CarRepository;
import com.freelkee.carmanager.response.BudgetOpportunities;
import com.freelkee.carmanager.response.CarBudgetAvailability;
import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.response.SellerResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final OwnerService ownerService;

    private final CarRepository carRepository;

    public CarService(OwnerService ownerService, final CarRepository carRepository) {
        this.ownerService = ownerService;
        this.carRepository = carRepository;
    }

    public List<CarResponse> getCars() {
        return carRepository.findAll().stream()
            .map(CarResponse::of)
            .collect(Collectors.toList());
    }

    public Set<Owner> getOwners(final Long carId) {
        return carRepository.getReferenceById(carId).getOwners();
    }

    public Set<Seller> getSellers(final Long carId) {
        return carRepository.getReferenceById(carId).getSellers();
    }


    public BudgetOpportunities getCarsByOwnerId(final Long ownerId) {
        return new BudgetOpportunities
            (
                carRepository.getByPriceLessThanEqual(ownerService.getOwner(ownerId).getBudget()).stream()
                    .map(car -> new CarBudgetAvailability
                        (
                            CarResponse.of(car),
                            car.getSellers().stream()
                                .map(SellerResponse::of)
                                .collect(Collectors.toList())
                        ))
                    .collect(Collectors.toList())
            );
    }

    public List<CarResponse> searchCars(final int minYear, final int maxYear, final int minPrice, final int maxPrice) {

        return carRepository.getAllByYearBetweenAndPriceBetweenOrderByYearAscPriceAsc
                (
                    minYear, maxYear, minPrice, maxPrice
                )
            .stream()
            .map(CarResponse::of)
            .collect(Collectors.toList());
    }
}
