package com.machinecoding.bookmyshow.model;

import com.machinecoding.bookmyshow.model.enums.SeatType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShowSeatType extends BaseEntity {
    @ManyToOne
    @JoinColumn
    private Show show;

    private SeatType seatType;

    private int price;
}
