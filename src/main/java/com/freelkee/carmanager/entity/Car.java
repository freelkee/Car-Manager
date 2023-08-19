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
            name = "availability",
            joinColumns = @JoinColumn(name = "cars"),
            inverseJoinColumns = @JoinColumn(name = "sellers"))
    private Set<Seller> sellers;

}
