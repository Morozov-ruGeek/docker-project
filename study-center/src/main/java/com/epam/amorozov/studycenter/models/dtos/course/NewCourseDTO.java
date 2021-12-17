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
public class NewCourseDTO {
    private String name;
    private List<Topic> topics;
    private BigDecimal amount;

    public NewCourseDTO(String name, List<Topic> topics) {
        this.name = name;
        this.topics = topics;
        this.amount = BigDecimal.ZERO;
    }

    public NewCourseDTO(NewCourseDTOBuilder builder) {
        this.name = builder.name;
        this.topics = builder.topics;
        this.amount = builder.amount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Course: ").append(name)
                .append(", topics: ").append(topics)
                .append(", amount = ").append(amount);
        return builder.toString();
    }
}
