package com.epam.amorozov.studycenter.utils.extractors;

import com.epam.amorozov.studycenter.models.entities.PaymentEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentEntityExtractor implements ResultSetExtractor<List<PaymentEntity>> {
    private static final String ID_PAYMENT_IN_DB_COLUMN = "id";
    private static final String STUDENT_ID_COLUMN = "student_id";
    private static final String COURSE_ID_COLUMN = "course_id";
    private static final String PAID_INFO_COLUMN = "is_paid";

    @Override
    public List<PaymentEntity> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, PaymentEntity> paymentEntityMap = new HashMap<>();
        while (rs.next()){
            long paymentEntityId = rs.getLong(ID_PAYMENT_IN_DB_COLUMN);
            PaymentEntity paymentEntity = paymentEntityMap.get(paymentEntityId);
            if(paymentEntity == null){
                paymentEntity = new PaymentEntity();
                paymentEntity.setId(rs.getLong(ID_PAYMENT_IN_DB_COLUMN));
                paymentEntity.setStudentId(rs.getLong(STUDENT_ID_COLUMN));
                paymentEntity.setCourseId(rs.getLong(COURSE_ID_COLUMN));
                paymentEntity.setPaid(rs.getBoolean(PAID_INFO_COLUMN));
            }
            paymentEntityMap.put(paymentEntityId, paymentEntity);
        }
        return new ArrayList<>(paymentEntityMap.values());
    }
}
