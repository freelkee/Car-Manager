package com.freelkee.carmanager.service;

import com.freelkee.carmanager.entity.Seller;
import com.freelkee.carmanager.repository.SellerRepository;
import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.response.ObjectCenter;
import com.freelkee.carmanager.response.SellerResponse;

import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final CoordinatesService coordinatesService;

    public SellerService(final SellerRepository sellerRepository, final CoordinatesService coordinatesService) {
        this.sellerRepository = sellerRepository;
        this.coordinatesService = coordinatesService;
    }

    public List<SellerResponse> getSellers() {
        final var sellers = sellerRepository.findAll();
        final List<SellerResponse> sellerResponses = new ArrayList<>();
        for (Seller seller : sellers) {
            final var sellerResponse = SellerResponse.of(seller);
            addCoordinatesInAddressOfSellerResponse(sellerResponse);
            sellerResponses.add(sellerResponse);
        }
        return sellerResponses;
    }

    private void addCoordinatesInAddressOfSellerResponse(final SellerResponse sellerResponse) {
        ObjectCenter coordinates = coordinatesService.getCoordinates(sellerResponse.getAddress());

        final var df = new DecimalFormat("#.######");
        final String formattedLat = df.format(coordinates.getLat()).replace(',', '.');
        final String formattedLan = df.format(coordinates.getLon()).replace(',', '.');

        sellerResponse.setAddress(String.format
            (
                "%s - %s, %s",
                sellerResponse.getAddress(),
                formattedLat,
                formattedLan
            )
        );
    }

    public List<CarResponse> getCars(final Long sellerId) {
        return sellerRepository.getReferenceById(sellerId).getCars()
            .stream()
            .map(CarResponse::of)
            .collect(Collectors.toList());
    }

    public SellerResponse getSeller(final Long id) {
        return SellerResponse.of(
            sellerRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Seller %d does not exist", id))));
    }
}
