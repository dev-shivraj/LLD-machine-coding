package com.machinecoding.splitwise.model;


import com.machinecoding.splitwise.model.enums.ExpenseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Expense extends BaseEntity{

    private String description;
    private int amount;

    @Enumerated(EnumType.ORDINAL)
    private ExpenseType expenseType;


    @ManyToOne
    private Group group;  // findAllByGroup

    @OneToMany(mappedBy = "expense")
    List<UserExpense> userExpenses;
}