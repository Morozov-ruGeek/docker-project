package com.epam.amorozov.paymentservice.repositories;

import com.epam.amorozov.paymentservice.models.PaymentEntity;

import java.util.Optional;

public interface PaymentRepository {
    Optional<PaymentEntity> getPaymentByPaymentId(Long paymentId);
    Optional<PaymentEntity> savePayment(PaymentEntity paymentEntity);
    Optional<PaymentEntity> findById(Long id);
}
