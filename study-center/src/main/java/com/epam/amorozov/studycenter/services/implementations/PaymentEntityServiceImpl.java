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
import org.springframework.web.client.HttpServerErrorException;

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
            log.info("Payment student: {}, course: {} completed", studentId, courseId);
            return "Payment completed";
        }
        log.debug("Payment student: {}, course: {} NOT completed", studentId, courseId);
        return "Payment NOT completed";
    }

    private PaymentEntity updatePayment(Long id) {
        return paymentEntityRepository.updatePaymentById(id).orElseThrow();
    }

    @Override
    @CircuitBreaker(name = STUDY_SERVICE)
    @Retry(name = STUDY_SERVICE)
    public Optional<PaymentEntity> saveNewPayment(Long studentId, Long courseId) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setStudentId(studentId);
        paymentEntity.setCourseId(courseId);
        return paymentEntityRepository.saveNewPayment(paymentEntity);
    }

    @Override
    @CircuitBreaker(name = STUDY_SERVICE)
    @Retry(name = STUDY_SERVICE)
    public List<CourseDTO> studentPaidCourses(Long id){
        return paymentEntityRepository.findStudentPayments(id).stream()
                .filter(paymentEntity -> !paymentEntity.isPaid())
                .map(PaymentEntity::getCourseId)
                .map(courseService::getCourseById)
                .collect(Collectors.toList());
    }

    private String fallbackPayForTheCourse (HttpServerErrorException ex){
        log.debug("Payment service is down with exception: {}", ex.toString());
        return "Payment service is down: " + ex.toString();
    }
}
