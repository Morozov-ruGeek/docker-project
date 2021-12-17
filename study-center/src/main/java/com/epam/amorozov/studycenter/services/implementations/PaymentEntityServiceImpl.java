package com.epam.amorozov.studycenter.services.implementations;

import com.epam.amorozov.studycenter.exceptions.ResourceNotFoundException;
import com.epam.amorozov.studycenter.models.dtos.course.CourseDTO;
import com.epam.amorozov.studycenter.models.entities.PaymentEntity;
import com.epam.amorozov.studycenter.repositories.PaymentEntityRepository;
import com.epam.amorozov.studycenter.services.CourseService;
import com.epam.amorozov.studycenter.services.PaymentEntityService;
import com.epam.amorozov.studycenter.soap.client.PaymentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentEntityServiceImpl implements PaymentEntityService {
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
    public PaymentEntity payForTheCourse(Long studentId, Long courseId) {
        PaymentEntity paymentEntity = paymentEntityRepository.findByStudentAndCourseIds(studentId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found paymentEntity with student id = " + studentId + ", course id = " + courseId));
        if (paymentClient.setPayment(paymentEntity.getId(), courseService.getCourseById(courseId).getAmount()).getPayment().getPaymentId() == paymentEntity.getId()) {
            return updatePayment(paymentEntity.getId());
        }
        return paymentEntity;
    }

    private PaymentEntity updatePayment(Long id) {
        return paymentEntityRepository.updatePaymentById(id).orElseThrow();
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
}
