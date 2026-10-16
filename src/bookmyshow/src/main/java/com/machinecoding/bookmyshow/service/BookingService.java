package com.machinecoding.bookmyshow.service;

import com.machinecoding.bookmyshow.exceptions.UserNotFoundException;
import com.machinecoding.bookmyshow.model.Booking;
import com.machinecoding.bookmyshow.model.User;
import com.machinecoding.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;

    @Autowired
    public BookingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Booking bookTicket(int showId, int userId, List<Integer> showSeatIds) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Invalid user id: " + userId);
        }

        return null;
    }
}
