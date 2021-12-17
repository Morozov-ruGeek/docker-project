package com.epam.amorozov.paymentservice.services.implementations;

import com.epam.amorozov.paymentservice.models.PaymentEntity;
import com.epam.amorozov.paymentservice.repositories.PaymentRepository;
import com.epam.amorozov.paymentservice.services.PaymentService;
import com.epam.amorozov.paymentservice.soaps.payments.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public static final Function<PaymentEntity, Payment> functionEntityToSoap = paymentEntity -> {
        Payment payment = new Payment();
        payment.setPaymentId(paymentEntity.getPaymentId());
        payment.setPaymentAmount(paymentEntity.getPaymentAmount());
        payment.setDateOfPayment(paymentEntity.getDateOfPayment());
        return payment;
    };

    @Override
    public Payment saveNewPayment(PaymentEntity paymentEntity) {
        return paymentRepository.savePayment(paymentEntity)
                .map(functionEntityToSoap).get();
    }

    @Override
    public Payment getPaymentByPaymentId(Long paymentId) {
        return paymentRepository.getPaymentByPaymentId(paymentId).map(functionEntityToSoap).get();
    }
}
