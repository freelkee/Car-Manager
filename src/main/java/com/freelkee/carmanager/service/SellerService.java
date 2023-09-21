package com.freelkee.carmanager.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freelkee.carmanager.repository.SellerRepository;
import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.response.SellerResponse;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;


    public SellerService(final SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public List<SellerResponse> getSellers() {
        return sellerRepository.findAll().stream()
            .map(SellerResponse::of)
            .peek(s -> {
                try {
                    s.setAddress(s.getAddress() + " - " + getCityCoordinates(s.getAddress()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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

    public static void main(String[] args) throws Exception {
        System.out.println(getCityCoordinates("Дальнегорск"));
    }

    public static String getCityCoordinates(String cityName) throws Exception {

        String encodedCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        String apiUrl = String.format("https://nominatim.openstreetmap.org/search?q=%s&format=json&polygon_geojson=1", encodedCityName);
        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.toString());

            double latitude = rootNode.get(0).get("lat").asDouble();
            double longitude = rootNode.get(0).get("lon").asDouble();

            return latitude + ", " + longitude;
        } else {
            return "[Failed to recognize coordinates]";
        }
    }

}
