package com.freelkee.carmanager.service;

import com.freelkee.carmanager.entity.Availability;
import com.freelkee.carmanager.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    @Autowired
    public AvailabilityService(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }
    public List<Availability> getAvailability() {
        return availabilityRepository.findAll();
    }
}
