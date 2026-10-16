package com.machinecoding.bookmyshow.dto;

import com.machinecoding.bookmyshow.model.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private int amount;
    private int bookingId;
    private ResponseStatus status;
    private String screenName;
    private String message;
}
