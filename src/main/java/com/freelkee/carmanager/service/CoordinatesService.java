package com.freelkee.carmanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freelkee.carmanager.response.ObjectCenter;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoordinatesService {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private static final String OPEN_STREET_MAP_URL =
        "https://nominatim.openstreetmap.org/search?q=%s&format=json&polygon_geojson=1";

    public CoordinatesService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Cacheable("geoObjects")
    public ObjectCenter getCoordinates(final String cityName) {
        final var fullApiUrl = String.format(OPEN_STREET_MAP_URL, cityName);
        final var stringJsonResponse = restTemplate.getForEntity(fullApiUrl, String.class).getBody();
        return objectMapper.readValue(stringJsonResponse, ObjectCenter.class);
    }
}
