package com.epam.amorozov.studycenter.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    private Long id;
    private Long studentId;
    private Long courseId;
    private boolean isPaid;

    public PaymentEntity(Long studentId, Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PaymentEntity: ").append(id)
                .append(", student id: ").append(studentId)
                .append(", course id: ").append(courseId)
                .append(", is paid: ").append(isPaid);
        return builder.toString();
    }
}
