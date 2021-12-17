package com.epam.amorozov.studycenter.utils.extractors;

import com.epam.amorozov.studycenter.models.entities.PaymentEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PaymentRowMapper implements RowMapper<PaymentEntity> {
    private static final String ID_PAYMENT_IN_DB_COLUMN = "id";
    private static final String STUDENT_ID_COLUMN = "student_id";
    private static final String COURSE_ID_COLUMN = "course_id";
    private static final String PAID_INFO_COLUMN = "is_paid";

    @Override
    public PaymentEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(resultSet.getLong(ID_PAYMENT_IN_DB_COLUMN));
        paymentEntity.setStudentId(resultSet.getLong(STUDENT_ID_COLUMN));
        paymentEntity.setCourseId(resultSet.getLong(COURSE_ID_COLUMN));
        paymentEntity.setPaid(resultSet.getBoolean(PAID_INFO_COLUMN));
        return paymentEntity;
    }
}
