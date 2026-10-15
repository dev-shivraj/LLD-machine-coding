package com.machinecoding.bookmyshow.model;

import com.machinecoding.bookmyshow.model.enums.TheaterStatus;
import jakarta.persistence.*;

@Entity
public class Theater extends BaseEntity {

    private String name;

//    in case we don't create enum and create class instead and have separate join table instead of status column
//    @ManyToOne
//    @JoinColumn(name = "value")
//    private TheaterStatus status;

    @Enumerated(EnumType.STRING)
    private TheaterStatus status;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
