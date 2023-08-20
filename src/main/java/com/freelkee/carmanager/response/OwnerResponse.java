package com.freelkee.carmanager.response;

import com.freelkee.carmanager.entity.Owner;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class OwnerResponse {

    private final Long id;

    private final String name;

    private final Long carId;

    public static OwnerResponse of(final Owner owner) {
        return OwnerResponse.builder()
            .id(owner.getId())
            .name(owner.getName())
            .carId(owner.getCar() == null ? null : owner.getCar().getId())
            .build();
    }

}