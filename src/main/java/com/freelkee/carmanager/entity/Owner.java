package com.freelkee.carmanager.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "owners")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Owner {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;


}