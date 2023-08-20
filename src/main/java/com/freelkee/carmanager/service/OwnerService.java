package com.freelkee.carmanager.service;

import com.freelkee.carmanager.repository.OwnerRepository;
import com.freelkee.carmanager.response.OwnerResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(final OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public List<OwnerResponse> getOwners() {
        return ownerRepository.findAll()
            .stream()
            .map(OwnerResponse::of)
            .collect(Collectors.toList());
    }
}
