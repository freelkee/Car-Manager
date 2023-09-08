package com.freelkee.carmanager.response;

import com.freelkee.carmanager.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class CarResponse {
    private final Long id;
    private final int price;
    private final int year;
    private final int enginePower;

    private final List<SellerResponse> sellers;

    public static CarResponse of(final Car car) {
        return CarResponse.builder()
            .id(car.getId())
            .price(car.getPrice())
            .year(car.getYear())
            .enginePower(car.getEnginePower())
            .sellers(car.getSellers().stream()
                .map(SellerResponse::of)
                .collect(Collectors.toList()))
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
                Objects.equals(id, carResponse.getId()) &&
                sellers == carResponse.getSellers();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, year, enginePower, sellers);
    }

}

