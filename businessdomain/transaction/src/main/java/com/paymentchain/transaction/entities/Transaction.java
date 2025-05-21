package com.paymentchain.transaction.entities;

import com.paymentchain.transaction.util.Channel;
import com.paymentchain.transaction.util.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String reference;
    private String ibanAccount;
    private LocalDateTime date;
    private double amount;
    private double fee;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Channel channel;

    // BusinessValidations
    public void applyBusinessValidations() {
        if (this.fee > 0) {
            this.amount -= this.fee;
        }

        if (this.amount == 0) {
            throw new IllegalArgumentException("the Amount of the transaction can't be 0");
        }

        if (this.date.isAfter(LocalDateTime.now())) {
            this.status = Status.PENDING;
        } else {
            this.status = Status.SETTLED;
        }
    }
}
