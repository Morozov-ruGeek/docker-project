package com.epam.amorozov.studycenter.models.dtos.course;

import com.epam.amorozov.studycenter.models.entities.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {
    private Long id;
    private String name;
    private List<Topic> topics;
    private BigDecimal amount;

    public CourseDTO(CourseDTOBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.topics = builder.topics;
        this.amount = builder.amount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Course: ")
                .append(id).append(" ")
                .append(name)
                .append(", topics: ").append(topics)
                .append(", amount = ").append(amount);
        return builder.toString();
    }
}
