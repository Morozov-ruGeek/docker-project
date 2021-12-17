package com.epam.amorozov.paymentservice.endpoints;

import com.epam.amorozov.paymentservice.models.PaymentEntity;
import com.epam.amorozov.paymentservice.services.PaymentService;
import com.epam.amorozov.paymentservice.soaps.payments.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PaymentEndpoint {

    private static final String NAMESPACE_URI = "http://www.amorozov.com/spring/ws/payments";
    private static final String LOCAL_PART_SET_PAYMENT_REQUEST = "setPaymentRequest";
    private static final String LOCAL_PART_GET_PAYMENT_BY_ID_REQUEST = "getPaymentByPaymentIdRequest";

    private final PaymentService paymentService;

    @Autowired
    public PaymentEndpoint(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = LOCAL_PART_SET_PAYMENT_REQUEST)
    @ResponsePayload
    public SetPaymentResponse setNewPayment(@RequestPayload SetPaymentRequest request) {
        PaymentEntity paymentEntity= new PaymentEntity(request.getPaymentId(), request.getPaymentAmount());
        SetPaymentResponse response = new SetPaymentResponse();
        Payment payment = paymentService.saveNewPayment(paymentEntity);
        response.setPayment(payment);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = LOCAL_PART_GET_PAYMENT_BY_ID_REQUEST)
    @ResponsePayload
    public GetPaymentByPaymentIdResponse getPaymentById(@RequestPayload GetPaymentByPaymentIdRequest request) {
        GetPaymentByPaymentIdResponse response = new GetPaymentByPaymentIdResponse();
        response.setPayment(paymentService.getPaymentByPaymentId(request.getPaymentId()));
        return response;
    }
}
