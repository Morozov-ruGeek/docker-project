package com.epam.amorozov.paymentservice.services;

import com.epam.amorozov.paymentservice.models.PaymentEntity;
import com.epam.amorozov.paymentservice.soaps.payments.Payment;

public interface PaymentService {
    Payment saveNewPayment(PaymentEntity paymentEntity);
    Payment getPaymentByPaymentId(Long paymentId);
}
