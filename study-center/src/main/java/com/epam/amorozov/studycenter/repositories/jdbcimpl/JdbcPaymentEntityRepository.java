package com.epam.amorozov.studycenter.repositories.jdbcimpl;

import com.epam.amorozov.studycenter.models.entities.PaymentEntity;
import com.epam.amorozov.studycenter.repositories.PaymentEntityRepository;
import com.epam.amorozov.studycenter.utils.ResourceReader;
import com.epam.amorozov.studycenter.utils.extractors.PaymentEntityExtractor;
import com.epam.amorozov.studycenter.utils.extractors.PaymentRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@ConditionalOnClass(DataSource.class)
@Slf4j
public class JdbcPaymentEntityRepository implements PaymentEntityRepository {
    private static final String GET_PAYMENT_BY_ID_SQL_QUERY_PATH = "classpath:queries/payment/find_payment_by_id.sql";
    private static final String SAVE_NEW_PAYMENT_SQL_QUERY_PATH = "classpath:queries/payment/save_new_payment.sql";
    private static final String UPDATE_COLUMN_IS_PAID_SQL_QUERY_PATH = "classpath:queries/payment/update_column_is_paid.sql";
    private static final String GET_PAYMENT_BY_STUDENT_AND_COURSE_IDS_SQL_QUERY_PATH = "classpath:queries/payment/find_payment_by_student_and_course_ids.sql";
    private static final String GET_PAYMENT_BY_STUDENT_ID_SQL_QUERY_PATH = "classpath:queries/payment/find_payment_by_student_id.sql";

    private final JdbcTemplate jdbcTemplate;
    private final PaymentRowMapper paymentRowMapper;
    private final GeneratedKeyHolder keyHolder;
    private final ResourceReader resourceReader;
    private final PaymentEntityExtractor paymentEntityExtractor;

    @Autowired
    public JdbcPaymentEntityRepository(JdbcTemplate jdbcTemplate,
                                       PaymentRowMapper paymentRowMapper,
                                       GeneratedKeyHolder keyHolder,
                                       ResourceReader resourceReader,
                                       PaymentEntityExtractor paymentEntityExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.paymentRowMapper = paymentRowMapper;
        this.keyHolder = keyHolder;
        this.resourceReader = resourceReader;
        this.paymentEntityExtractor = paymentEntityExtractor;
    }

    @Override
    public Optional<PaymentEntity> saveNewPayment(PaymentEntity paymentEntity) {
        final String savePaymentSql = resourceReader.readFileToString(SAVE_NEW_PAYMENT_SQL_QUERY_PATH);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(savePaymentSql, new String[]{"id"});
                preparedStatement.setLong(1, paymentEntity.getStudentId());
                preparedStatement.setLong(2, paymentEntity.getCourseId());
                return preparedStatement;
            }, keyHolder);

            long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
            return findById(id);
        } catch (Exception e) {
            log.error("Can`t save paymentEntity({}) in DB", paymentEntity);
            return Optional.empty();
        }
    }

    @Override
    public Optional<PaymentEntity> findById(Long id) {
        final String findByIdSQL = resourceReader.readFileToString(GET_PAYMENT_BY_ID_SQL_QUERY_PATH);
        PaymentEntity paymentEntity = null;
        try {
            paymentEntity = jdbcTemplate.queryForObject(findByIdSQL, paymentRowMapper, id);
            log.debug("PaymentEntity with id = {} found", id);
        } catch (EmptyResultDataAccessException e) {
            log.debug("PaymentEntity with id = {} not found", id);
        }
        return Optional.ofNullable(paymentEntity);
    }

    @Override
    public Optional<PaymentEntity> updatePaymentById(Long id) {
        final String updatePaymentInfoSql = resourceReader.readFileToString(UPDATE_COLUMN_IS_PAID_SQL_QUERY_PATH);
        jdbcTemplate.update(updatePaymentInfoSql, id);
        return findById(id);
    }

    @Override
    public List<PaymentEntity> findStudentPayments(Long id) {
        String getAllStudentsSql = resourceReader.readFileToString(GET_PAYMENT_BY_STUDENT_ID_SQL_QUERY_PATH);
        return jdbcTemplate.query(getAllStudentsSql, paymentEntityExtractor);
    }

    @Override
    public Optional<PaymentEntity> findByStudentAndCourseIds(Long studentId, Long courseId) {
        final String findByStudentAndCourseIdsSQL = resourceReader.readFileToString(GET_PAYMENT_BY_STUDENT_AND_COURSE_IDS_SQL_QUERY_PATH);
        Object[] args = new Object[]{studentId, courseId};
        PaymentEntity paymentEntity = null;
        try{
            paymentEntity = jdbcTemplate.queryForObject(findByStudentAndCourseIdsSQL, paymentRowMapper, args);
        } catch (EmptyResultDataAccessException e){
            log.debug("PaymentEntity with student_id = {}, nad course_id = {} not found", studentId, courseId);
        }
        return Optional.ofNullable(paymentEntity);
    }
}
