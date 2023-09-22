package com.freelkee.carmanager.service;

import com.freelkee.carmanager.repository.SellerRepository;
import com.freelkee.carmanager.response.CarResponse;
import com.freelkee.carmanager.response.SellerResponse;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

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
                final var response = in.lines().collect(Collectors.joining());
                in.close();

                final var jsonArray = new JSONArray(response);
                if (jsonArray.length() > 0) {
                    final var jsonObject = jsonArray.getJSONObject(0);
                    final var geojsonObject = jsonObject.getJSONObject("geojson");
                    final var coordinatesArray = geojsonObject.getJSONArray("coordinates");

                    final var coordinatesList = new ArrayList<double[]>();
                    for (int i = 0; i < coordinatesArray.length(); i++) {
                        final var polygon = coordinatesArray.getJSONArray(i);
                        for (int j = 0; j < polygon.length(); j++) {
                            final var coordinates = polygon.getJSONArray(j);
                            final var longitude = coordinates.getDouble(0);
                            final var latitude = coordinates.getDouble(1);
                            coordinatesList.add(new double[]{longitude, latitude});
                        }
                    }

                    final var lat = coordinatesList.stream()
                        .map(coordinate -> coordinate[0])
                        .collect(Collectors.toList());
                    final var lan = coordinatesList.stream()
                        .map(coordinate -> coordinate[1])
                        .collect(Collectors.toList());

                    final var latAverage = lat.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0.0);
                    final var lanAverage = lan.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0.0);

                    final var df = new DecimalFormat("#.#######");
                    final var formattedLat = df.format(latAverage).replace(',', '.');
                    final var formattedLan = df.format(lanAverage).replace(',', '.');

                    return formattedLat + ", " + formattedLan;
                } else {
                    return "Нет данных о координатах.";
                }
            } else {
                return "Ошибка при выполнении запроса. Код ответа: " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при выполнении запроса.";
        }
    }
}
