package com.freelkee.carmanager.response;

import com.freelkee.carmanager.entity.Seller;
import lombok.*;

import java.util.Objects;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class SellerResponse {

    private Long id;

    private String name;

    public static SellerResponse of(final Seller seller) {
        return SellerResponse.builder()
            .id(seller.getId())
            .name(seller.getName())
            .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellerResponse that = (SellerResponse) o;
        return id.equals(that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}