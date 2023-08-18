package com.freelkee.carmanager.response;

import com.freelkee.carmanager.entity.Availability;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class AvailabilityResponse {
    private final Long id;
    private final Long sellerId;
    private final Long carId;
    public static AvailabilityResponse of(Availability availability){
        return AvailabilityResponse.builder()
                .id(availability.getId())
                .sellerId(availability.getSeller().getId())
                .carId(availability.getCar().getId())
                .build();
    }

}
