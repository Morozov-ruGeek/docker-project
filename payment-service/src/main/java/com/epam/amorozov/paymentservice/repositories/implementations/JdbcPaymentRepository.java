package com.epam.amorozov.paymentservice.repositories.implementations;

import com.epam.amorozov.paymentservice.models.PaymentEntity;
import com.epam.amorozov.paymentservice.repositories.PaymentRepository;
import com.epam.amorozov.paymentservice.utils.PaymentRowMapper;
import com.epam.amorozov.paymentservice.utils.ResourceReader;
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
import java.util.Objects;
import java.util.Optional;

@Repository
@ConditionalOnClass(DataSource.class)
@Slf4j
public class JdbcPaymentRepository implements PaymentRepository {

    private static final String GET_PAYMENT_BY_PAYMENT_ID_SQL_QUERY_PATH = "classpath:queries/find_payment_by_payment_id.sql";
    private static final String SAVE_PAYMENT_IN_DB_SQL_QUERY_PATH = "classpath:queries/save_new_payment.sql";
    private static final String GET_PAYMENT_BY_ID_SQL_QUERY_PATH = "classpath:queries/find_payment_by_id.sql";


    private final JdbcTemplate jdbcTemplate;
    private final PaymentRowMapper paymentRowMapper;
    private final ResourceReader resourceReader;

    @Autowired
    public JdbcPaymentRepository(JdbcTemplate jdbcTemplate,
                                 PaymentRowMapper paymentRowMapper,
                                 ResourceReader resourceReader) {
        this.jdbcTemplate = jdbcTemplate;
        this.paymentRowMapper = paymentRowMapper;
        this.resourceReader = resourceReader;
    }

    @Override
    public Optional<PaymentEntity> getPaymentByPaymentId(Long paymentId) {
        PaymentEntity paymentEntity = null;
        final String getPaymentSql = resourceReader.readFileToString(GET_PAYMENT_BY_PAYMENT_ID_SQL_QUERY_PATH);
        try {
            paymentEntity = jdbcTemplate.queryForObject(getPaymentSql, paymentRowMapper, paymentId);
            log.debug("PaymentEntity with payment_id = {} found", paymentId);
        } catch (EmptyResultDataAccessException e) {
            log.debug("PaymentEntity with payment_id = {} not found", paymentId);
        }
        return Optional.ofNullable(paymentEntity);
    }

    @Override
    public Optional<PaymentEntity> savePayment(PaymentEntity paymentEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String savePaymentSql = resourceReader.readFileToString(SAVE_PAYMENT_IN_DB_SQL_QUERY_PATH);
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(savePaymentSql, new String[]{"ID"});
                preparedStatement.setLong(1, paymentEntity.getPaymentId());
                preparedStatement.setBigDecimal(2, paymentEntity.getPaymentAmount());
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
        final String findByIdSql = resourceReader.readFileToString(GET_PAYMENT_BY_ID_SQL_QUERY_PATH);
        PaymentEntity paymentEntity = null;
        try {
            paymentEntity = jdbcTemplate.queryForObject(findByIdSql, paymentRowMapper, id);
            log.debug("PaymentEntity with id = {} found", id);
        } catch (EmptyResultDataAccessException e) {
            log.debug("PaymentEntity with id = {} not found", id);
        }
        return Optional.ofNullable(paymentEntity);
    }
}
