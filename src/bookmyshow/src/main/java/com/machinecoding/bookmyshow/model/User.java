package com.machinecoding.bookmyshow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity{
    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;
}
