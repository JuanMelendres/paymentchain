package com.paymentchain.transaction.entities;

import com.paymentchain.transaction.exception.BusinessRuleException;
import com.paymentchain.transaction.util.Channel;
import com.paymentchain.transaction.util.Status;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.http.HttpStatus;

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
    public void applyBusinessValidations() throws BusinessRuleException {
        if (this.fee > 0) {
            this.amount -= this.fee;
        }

        if (this.amount == 0) {
            throw new BusinessRuleException("INVALID_AMOUNT", "The Amount of the transaction can't be 0", HttpStatus.BAD_REQUEST);
        }

        if (this.date.isAfter(LocalDateTime.now())) {
            this.status = Status.PENDING;
        } else {
            this.status = Status.SETTLED;
        }
    }
}
