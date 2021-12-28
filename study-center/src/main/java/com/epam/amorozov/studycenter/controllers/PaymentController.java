package com.epam.amorozov.studycenter.controllers;

import com.epam.amorozov.studycenter.services.PaymentEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payments")
public class PaymentController {

    private final PaymentEntityService paymentEntityService;

    @Autowired
    public PaymentController(PaymentEntityService paymentEntityService) {
        this.paymentEntityService = paymentEntityService;
    }

    @PostMapping("/{studentId}, {courseId}")
    @ResponseStatus(HttpStatus.OK)
    public void payForTheCourse(@PathVariable Long studentId,
                                         @PathVariable Long courseId) {
        paymentEntityService.payForTheCourse(studentId, courseId);
    }
//todo
//    @GetMapping("/{studentId}")
//    public List<CourseDTO> studentIsPaidCourses(@PathVariable Long studentId){
//        return paymentEntityService.studentPaidCourses(studentId);
//    }
}
