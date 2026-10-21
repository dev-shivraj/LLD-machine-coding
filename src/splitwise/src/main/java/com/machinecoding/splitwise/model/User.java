package com.machinecoding.splitwise.model;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    private String name;
    private String phoneNumber;
    private String password;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Expense> expenses;
}