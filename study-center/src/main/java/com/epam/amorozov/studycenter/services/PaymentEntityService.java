package com.epam.amorozov.studycenter.services;

import com.epam.amorozov.studycenter.models.dtos.course.CourseDTO;
import com.epam.amorozov.studycenter.models.entities.PaymentEntity;

import java.util.List;
import java.util.Optional;

public interface PaymentEntityService {
    PaymentEntity payForTheCourse(Long studentId, Long courseId);
    Optional<PaymentEntity> saveNewPayment(Long studentId, Long courseId);
    List<CourseDTO> studentPaidCourses(Long id);
}
