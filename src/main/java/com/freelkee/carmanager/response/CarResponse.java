package com.freelkee.carmanager.response;

import com.freelkee.carmanager.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class CarResponse {
    private final Long id;
    private final int price;
    private final int year;
    private final int enginePower;

    public static CarResponse of(final Car car) {
        return CarResponse.builder()
            .id(car.getId())
            .price(car.getPrice())
            .year(car.getYear())
            .enginePower(car.getEnginePower())
            .build();
    }

    @Override
    public boolean equals(final Object o) {
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

