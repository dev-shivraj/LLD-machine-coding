package com.machinecoding.bookmyshow.model;

import com.machinecoding.bookmyshow.model.enums.Feature;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Screen extends BaseEntity{

    private String name;

    @ManyToOne
    private Theater theater;

    @OneToMany
    @JoinColumn
    private List<Seat> seats;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Feature> features;
}
