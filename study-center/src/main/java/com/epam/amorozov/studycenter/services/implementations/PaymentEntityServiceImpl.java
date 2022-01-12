package com.epam.amorozov.studycenter.services.implementations;

import com.epam.amorozov.studycenter.exceptions.ResourceNotFoundException;
import com.epam.amorozov.studycenter.models.dtos.course.CourseDTO;
import com.epam.amorozov.studycenter.models.entities.PaymentEntity;
import com.epam.amorozov.studycenter.repositories.PaymentEntityRepository;
import com.epam.amorozov.studycenter.services.CourseService;
import com.epam.amorozov.studycenter.services.PaymentEntityService;
import com.epam.amorozov.studycenter.soap.client.PaymentClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentEntityServiceImpl implements PaymentEntityService {

    private static final String STUDY_SERVICE = "studyService";

    private final PaymentEntityRepository paymentEntityRepository;
    private final CourseService courseService;
    private final PaymentClient paymentClient;

    @Autowired
    public PaymentEntityServiceImpl(PaymentEntityRepository paymentEntityRepository,
                                    CourseService courseService,
                                    PaymentClient paymentClient) {
        this.paymentEntityRepository = paymentEntityRepository;
        this.courseService = courseService;
        this.paymentClient = paymentClient;
    }

    @Override
    @CircuitBreaker(name = STUDY_SERVICE, fallbackMethod = "fallbackPayForTheCourse")
    @Retry(name = STUDY_SERVICE)
    public String payForTheCourse(Long studentId, Long courseId) {
        PaymentEntity paymentEntity = paymentEntityRepository.findByStudentAndCourseIds(studentId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found paymentEntity with student id = " + studentId + ", course id = " + courseId));
        if (paymentClient.setPayment(paymentEntity.getId(), courseService.getCourseById(courseId).getAmount()).getPayment().getPaymentId() == paymentEntity.getId()) {
            log.info("Payment is up: student - {}, course - {}", studentId, courseId);
            updatePayment(paymentEntity.getId());
            return "Payment completed";
        }
        log.debug("Payment is down: .Student - {}, course - {}",studentId, courseId);
        return "Payment Service not comleted";
    }

    private void updatePayment(Long paymentEntityId) {
        if (paymentEntityRepository.updatePaymentById(paymentEntityId).isEmpty()) {
            log.debug("PaymentEntity not updated. Id - {}", paymentEntityId);
        } else {
            log.info("PaymentEntity updated. Id - {}", paymentEntityId);
        }
    }

    @Override
    public Optional<PaymentEntity> saveNewPayment(Long studentId, Long courseId) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setStudentId(studentId);
        paymentEntity.setCourseId(courseId);
        return paymentEntityRepository.saveNewPayment(paymentEntity);
    }

    @Override
    public List<CourseDTO> studentPaidCourses(Long id){
        return paymentEntityRepository.findStudentPayments(id).stream()
                .filter(paymentEntity -> !paymentEntity.isPaid())
                .map(PaymentEntity::getCourseId)
                .map(courseService::getCourseById)
                .collect(Collectors.toList());
    }

    private String fallbackPayForTheCourse(Exception ex){
        log.debug("Payment Service down with exception: {}", ex.toString());
        return "Payment Service down";
    }
}
