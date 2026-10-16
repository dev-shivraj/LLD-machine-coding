package com.machinecoding.bookmyshow.controller;

import com.machinecoding.bookmyshow.dto.BookingRequest;
import com.machinecoding.bookmyshow.dto.BookingResponse;
import com.machinecoding.bookmyshow.model.Booking;
import com.machinecoding.bookmyshow.model.enums.ResponseStatus;
import com.machinecoding.bookmyshow.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    BookingResponse boolTicket(BookingRequest bookingRequest) {
        BookingResponse response = new BookingResponse();

        try {
            Booking booking = bookingService.bookTicket(bookingRequest.getShowId(), bookingRequest.getUserId(), bookingRequest.getShowSeatIds());
            response.setBookingId(booking.getId());
            response.setAmount(booking.getAmount());
            response.setScreenName(booking.getShow().getScreen().getName());
            response.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            response.setStatus(ResponseStatus.FAILED);
            response.setMessage("Booking request failed.");
        }

        return response;
    }
}
