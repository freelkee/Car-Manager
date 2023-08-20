package com.freelkee.carmanager.response;

import com.freelkee.carmanager.entity.Car;
import lombok.*;
@Setter
@Getter
@Builder
@AllArgsConstructor
public class CarResponse {

    private Long id;

    private int price;

    private int year;

    private int enginePower;

    public static CarResponse of(final Car car) {
        return CarResponse.builder()
            .id(car.getId())
            .price(car.getPrice())
            .year(car.getYear())
            .enginePower(car.getEnginePower())
            .build();
    }

}
