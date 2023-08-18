package com.freelkee.carmanager.service;

import com.freelkee.carmanager.repository.AvailabilityRepository;
import com.freelkee.carmanager.response.AvailabilityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    @Autowired
    public AvailabilityService(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }
    public List<AvailabilityResponse> getAvailability() {
        return availabilityRepository.findAll().stream().map(AvailabilityResponse::of).collect(Collectors.toList());
    }
}
