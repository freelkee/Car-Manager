package com.freelkee.carmanager.response;

import com.freelkee.carmanager.entity.Car;
import lombok.*;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarResponse carResponse = (CarResponse) o;
        return
            price == carResponse.getPrice() &&
                year == carResponse.getYear() &&
                enginePower == carResponse.getEnginePower() &&
                Objects.equals(id, carResponse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, year, enginePower);
    }
}
