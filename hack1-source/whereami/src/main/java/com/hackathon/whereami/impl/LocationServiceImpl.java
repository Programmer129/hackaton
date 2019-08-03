package com.hackathon.whereami.impl;

import com.hackathon.whereami.api.LocationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
@PropertySource("classpath:application.properties")
class LocationServiceImpl implements LocationService {

    private static final String REQUEST = "https://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&key=%s";

    private final RestTemplate restTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${geocode.api.key}")
    private String apiKey;

    LocationServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    @Transactional
    public String saveAndDetectLocation(double latitude, double longitude) {
        GeocodeResult result = restTemplate.getForObject(String.format(REQUEST, latitude, longitude, apiKey), GeocodeResult.class);

        String address = result.getPlus_code().getCompound_code();

        LocationEntity entity = new LocationEntity(latitude, longitude, address);

        entityManager.persist(entity);

        return address;
    }
}
