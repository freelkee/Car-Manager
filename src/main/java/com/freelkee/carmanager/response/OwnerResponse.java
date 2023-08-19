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

    public static OwnerResponse of(Owner owner) {
        OwnerResponseBuilder ownerResponseBuilder = OwnerResponse.builder()
                .id(owner.getId())
                .name(owner.getName());
        if (owner.getCar() == null) {
            return ownerResponseBuilder.carId(null).build();
        } else return ownerResponseBuilder.carId(owner.getCar().getId()).build();
    }

}