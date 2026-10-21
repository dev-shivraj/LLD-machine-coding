package com.machinecoding.splitwise.model;


import com.machinecoding.splitwise.model.enums.UserExpenseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserExpense extends BaseEntity{

    private int amount;

    @Enumerated(EnumType.STRING)
    private UserExpenseType userExpenseType;

    @ManyToOne
    @JoinColumn
    private Expense expense;

    @ManyToOne
    @JoinColumn
    private User user;
}
