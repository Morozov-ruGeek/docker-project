package com.epam.amorozov.paymentservice.utils;

import com.epam.amorozov.paymentservice.models.PaymentEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PaymentRowMapper implements RowMapper<PaymentEntity> {
    private static final String ID_PAYMENT_IN_DB_COLUMN = "id";
    private static final String PAYMENT_ID_COLUMN = "payment_id";
    private static final String PAYMENT_AMOUNT_COLUMN = "payment_amount";
    private static final String DATE_OF_PAYMENT_COLUMN = "date_of_payment";

    @Override
    public PaymentEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(resultSet.getLong(ID_PAYMENT_IN_DB_COLUMN));
        paymentEntity.setPaymentId(resultSet.getLong(PAYMENT_ID_COLUMN));
        paymentEntity.setPaymentAmount(resultSet.getBigDecimal(PAYMENT_AMOUNT_COLUMN));
        paymentEntity.setDateOfPayment(resultSet.getString(DATE_OF_PAYMENT_COLUMN));
        return paymentEntity;
    }
}
