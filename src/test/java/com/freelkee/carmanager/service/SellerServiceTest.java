package com.freelkee.carmanager.service;

import com.freelkee.carmanager.BaseTestContainersTest;
import com.freelkee.carmanager.response.ObjectCenter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SellerServiceTest extends BaseTestContainersTest {
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private CoordinatesService coordinatesService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void test() throws IOException {
        final var properties = new Properties();
        final InputStream input = getClass().getClassLoader().getResourceAsStream("application-test.properties");
        properties.load(input);
        final var expectedResponse = properties.getProperty("expected.response");

        Mockito.when(restTemplate.getForEntity
            (
                Mockito.anyString(),
                Mockito.any()
            )
        ).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        coordinatesService.getCoordinates("Дальнегорск");
        coordinatesService.getCoordinates("Дальнегорск");

        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(
            Mockito.anyString(),
            Mockito.any());

        final Cache coordinatesCache = cacheManager.getCache("geoObjects");
        assertNotNull(coordinatesCache);
        final String cacheKey = "Дальнегорск";
        final ObjectCenter cachedValue = coordinatesCache.get(cacheKey, ObjectCenter.class);
        assertNotNull(cachedValue);
    }
}