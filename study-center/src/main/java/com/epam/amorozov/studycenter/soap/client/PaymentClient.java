package com.epam.amorozov.studycenter.soap.client;

import com.epam.amorozov.studycenter.exceptions.ResourceNotFoundException;
import com.epam.amorozov.studycenter.wsdl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.SoapFaultClientException;

import java.math.BigDecimal;

@Slf4j
public class PaymentClient extends WebServiceGatewaySupport {

    private static final ObjectFactory objectFactory = new ObjectFactory();

    public SetPaymentResponse setPayment(Long paymentId, BigDecimal paymentAmount) {
        SetPaymentRequest request = objectFactory.createSetPaymentRequest();
        request.setPaymentId(paymentId);
        request.setPaymentAmount(paymentAmount);
        try {
            return (SetPaymentResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(getDefaultUri() + "/payments/payment", request);
        } catch (SoapFaultClientException e) {
            log.error(e.getMessage());
        }
        throw new ResourceNotFoundException("No request from SOAP");
    }


    public GetPaymentByPaymentIdResponse getPaymentBYPaymentId(Long paymentId) throws SoapFaultClientException {
        GetPaymentByPaymentIdRequest request = objectFactory.createGetPaymentByPaymentIdRequest();
        request.setPaymentId(paymentId);

        try {
            return (GetPaymentByPaymentIdResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(getDefaultUri() + "/payments/payment", request);
        } catch (SoapFaultClientException e) {
            log.error(e.getMessage());
        }
        throw new ResourceNotFoundException("No request from SOAP");
    }
}
