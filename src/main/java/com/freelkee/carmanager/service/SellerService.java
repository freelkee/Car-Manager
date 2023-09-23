package com.freelkee.carmanager.service;

import com.freelkee.carmanager.repository.SellerRepository;
import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.response.SellerResponse;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
            .peek(s -> s.setAddress(s.getAddress() + " - " + getCoordinates(s.getAddress())))
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


    public static String getCoordinates(String cityName) {
        try {
            final var encodedCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
            final var apiUrl = String.format("https://nominatim.openstreetmap.org/search?q=%s&format=json&polygon_geojson=1", encodedCityName);
            final var url = new URL(apiUrl);
            final var conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            final var responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                final var in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                final var response = in.lines().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
                in.close();

                final var objectMapper = new ObjectMapper();
                final var rootNode = objectMapper.readTree(response);

                final var jsonArray = (ArrayNode) rootNode;
                if (jsonArray.size() > 0) {
                    final var jsonObject = jsonArray.get(0);
                    final var geojsonObject = jsonObject.get("geojson");
                    final var coordinatesArray = geojsonObject.get("coordinates");

                    final var coordinatesList = new ArrayList<double[]>();
                    if (geojsonObject.get("type").asText().equals("Polygon")) {
                        addCoordinates(coordinatesList, coordinatesArray);
                    } else if (geojsonObject.get("type").asText().equals("MultiPolygon")) {
                        for (final JsonNode polygon : coordinatesArray) {
                            addCoordinates(coordinatesList, polygon);
                        }
                    }
                    final var lat = coordinatesList.stream().map(coordinate -> coordinate[1]).collect(Collectors.toList());
                    final var lan = coordinatesList.stream().map(coordinate -> coordinate[0]).collect(Collectors.toList());

                    final var latAverage = lat.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                    final var lanAverage = lan.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

                    final var df = new DecimalFormat("#.#######");
                    final var formattedLat = df.format(latAverage).replace(',', '.');
                    final var formattedLan = df.format(lanAverage).replace(',', '.');

                    return  formattedLat + ", " + formattedLan;
                } else {
                    return "There is no data about coordinates.";
                }
            } else {
                return "Error when executing the request. Response code: " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error when executing the request.";
        }
    }

    private static void addCoordinates(List<double[]> coordinatesList, JsonNode coordinatesNode) {
        for (int i = 0; i < coordinatesNode.size(); i++) {
            final var coordinates = coordinatesNode.get(i);
            for (int j = 0; j < coordinates.size(); j++) {
                final var longitude = coordinates.get(j).get(0).asDouble();
                final var latitude = coordinates.get(j).get(1).asDouble();
                coordinatesList.add(new double[]{longitude, latitude});
            }
        }
    }
}
