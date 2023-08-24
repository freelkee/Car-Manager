package com.freelkee.carmanager.response;

import com.freelkee.carmanager.entity.Seller;
import lombok.*;

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

}