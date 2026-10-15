package com.machinecoding.bookmyshow.model;

import com.machinecoding.bookmyshow.model.enums.PaymentMode;
import com.machinecoding.bookmyshow.model.enums.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Payment extends BaseEntity {

    private Double amount;

    private Date paymentDate;

//    @Enumerated(value = EnumType.STRING)
//    private PaymentGateway paymentGateway;

    @Enumerated(value = EnumType.ORDINAL)
    private PaymentStatus paymentStatus;

    @Enumerated(value = EnumType.STRING)
    private PaymentMode paymentMode;

    private String refNumber;
}
