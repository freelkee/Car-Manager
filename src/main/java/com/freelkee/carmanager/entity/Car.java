package com.freelkee.carmanager.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cars")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "sellers_to_cars",
        joinColumns = @JoinColumn(name = "car_id"),
        inverseJoinColumns = @JoinColumn(name = "seller_id"))
    private Set<Seller> sellers;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Set<Owner> owners;


}
