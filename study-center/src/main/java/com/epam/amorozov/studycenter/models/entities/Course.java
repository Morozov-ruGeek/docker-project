package com.epam.amorozov.studycenter.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    private Long id;
    private String name;
    private List<Topic> topics;
    private BigDecimal amount;

    public Course(String name, List<Topic> topics) {
        this.name = name;
        this.topics = topics;
        this.amount = BigDecimal.ZERO;
    }

    public void addTopic(Topic topic) {
        topics.add(topic);
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
