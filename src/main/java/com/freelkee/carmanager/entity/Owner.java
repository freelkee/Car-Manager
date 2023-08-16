package com.freelkee.carmanager.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "owners")
@NoArgsConstructor
@Getter
@Setter
public class Owner {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "price")
    private String name;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

}