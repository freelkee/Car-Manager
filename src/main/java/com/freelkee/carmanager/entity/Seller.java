package com.freelkee.carmanager.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sellers")
@NoArgsConstructor
@Getter
@Setter
public class Seller {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "availability",
            joinColumns = @JoinColumn(name = "sellers"),
            inverseJoinColumns = @JoinColumn(name = "cars"))
    private Set<Car> cars;


}