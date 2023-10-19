package com.freelkee.carmanager.service;

import com.freelkee.carmanager.repository.SellerRepository;
import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.response.ObjectCenter;
import com.freelkee.carmanager.response.SellerResponse;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SellerService {

    private static final String OPEN_STREET_MAP_URL =
        "https://nominatim.openstreetmap.org/search?q=%s&format=json&polygon_geojson=1";
    private final SellerRepository sellerRepository;

    private final RestTemplate restTemplate;


    public SellerService(final SellerRepository sellerRepository, final RestTemplate restTemplate) {
        this.sellerRepository = sellerRepository;
        this.restTemplate = restTemplate;
    }

    @Cacheable("sellersCache")
    public List<SellerResponse> getSellers() {
        return sellerRepository.findAll().stream()
            .map(seller -> {
                final var sellerResponse = SellerResponse.of(seller);
                addCoordinatesInAddressOfSellerResponse(sellerResponse);
                return sellerResponse;
            })
            .collect(Collectors.toList());
    }

    private void addCoordinatesInAddressOfSellerResponse(SellerResponse sellerResponse) {
        ObjectCenter coordinates = getCoordinates(sellerResponse.getAddress());

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
        return SellerResponse.of(sellerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(String.format("Seller %d does not exist", id))));
    }
    @Cacheable("coordinatesCache")
    public ObjectCenter getCoordinates(String cityName) {
        final var fullApiUrl = String.format(OPEN_STREET_MAP_URL, cityName);

        return restTemplate
            .exchange(fullApiUrl, HttpMethod.GET, null, ObjectCenter.class)
            .getBody();
    }
}
