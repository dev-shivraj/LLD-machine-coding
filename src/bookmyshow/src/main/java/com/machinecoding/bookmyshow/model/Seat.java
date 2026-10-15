package com.machinecoding.bookmyshow.model;

import com.machinecoding.bookmyshow.model.enums.SeatType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Seat extends BaseEntity{

    private String seatName;

    private int rowVal;

    private int columnVal;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;
}
