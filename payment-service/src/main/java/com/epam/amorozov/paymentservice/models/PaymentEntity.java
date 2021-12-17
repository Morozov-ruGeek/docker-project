package com.epam.amorozov.paymentservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    private Long id;
    private Long paymentId;
    private BigDecimal paymentAmount;
    private String dateOfPayment;

    public PaymentEntity(Long paymentId, BigDecimal paymentAmount) {
        this.paymentId = paymentId;
        this.paymentAmount = paymentAmount;
        this.dateOfPayment = String.valueOf(LocalDate.now());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PaymentEntity: ").append(id)
                .append(", payment_id: ").append(paymentId)
                .append(", amount: ").append(paymentAmount)
                .append(", date: ").append(dateOfPayment);
        return builder.toString();
    }
}
