package com.freelkee.carmanager.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cars")
@NoArgsConstructor
@Getter
@Setter
public class Car {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private int price;

    @Column(name = "year")
    private int year;

    @Column(name = "engine_power")
    private int enginePower;

    @ManyToMany
    @JoinTable(
        name = "sellers_to_cars",
        joinColumns = @JoinColumn(name = "car_id"),
        inverseJoinColumns = @JoinColumn(name = "seller_id"))
    private Set<Seller> sellers;

    @OneToMany
    @JoinColumn(name = "car_id")
    private Set<Owner> owners;
}
