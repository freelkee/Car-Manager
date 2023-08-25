package com.freelkee.carmanager.response;

import com.freelkee.carmanager.entity.Owner;
import lombok.*;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerResponse that = (OwnerResponse) o;
        return
            id.equals(that.id) &&
                Objects.equals(name, that.name)
                && Objects.equals(carId, that.carId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, carId);
    }
}