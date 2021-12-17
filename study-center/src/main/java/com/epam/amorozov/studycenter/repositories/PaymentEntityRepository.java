package com.epam.amorozov.studycenter.repositories;

import com.epam.amorozov.studycenter.models.entities.PaymentEntity;

import java.util.List;
import java.util.Optional;

public interface PaymentEntityRepository {
    Optional<PaymentEntity> saveNewPayment(PaymentEntity paymentEntity);
    Optional<PaymentEntity> findById(Long id);
    Optional<PaymentEntity> updatePaymentById(Long id);
    List<PaymentEntity> findStudentPayments(Long id);
    Optional<PaymentEntity> findByStudentAndCourseIds(Long studentId, Long courseId);
}
