package com.freelkee.carmanager.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="availability")
@NoArgsConstructor
@Setter
@Getter
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "car_seller_id")
    private CarSeller carSeller;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

}
