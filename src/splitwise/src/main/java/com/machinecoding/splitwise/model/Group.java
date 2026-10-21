package com.machinecoding.splitwise.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_group")
public class Group extends BaseEntity{

    private String name;

    @ManyToMany
    private List<User> members;

    @OneToMany(mappedBy = "group")
    private List <Expense> expenses;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User admin;
}
