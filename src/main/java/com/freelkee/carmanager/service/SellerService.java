package com.freelkee.carmanager.service;

import com.freelkee.carmanager.repository.SellerRepository;
import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.response.ObjectCenter;
import com.freelkee.carmanager.response.SellerResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SellerService {

    private static final String OPEN_STREET_MAP_URL = "https://nominatim.openstreetmap.org/search?q=%s&format=json&polygon_geojson=1";
    private final SellerRepository sellerRepository;


    public SellerService(final SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public List<SellerResponse> getSellers() {
        return sellerRepository.findAll().stream()
            .map(SellerResponse::of)
            .peek(sellerResponse -> {
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
            })
            .collect(Collectors.toList());
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


    public static ObjectCenter getCoordinates(String cityName) {
        final var apiUrl = String.format(OPEN_STREET_MAP_URL, cityName);

        return new RestTemplate()
            .exchange(apiUrl, HttpMethod.GET, null, ObjectCenter.class)
            .getBody();
    }
}
