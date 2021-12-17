package com.epam.amorozov.studycenter.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    private Long id;
    private String name;
    private Integer hoursDuration;

    public Topic(String name, Integer hoursDuration) {
        this.name = name;
        this.hoursDuration = hoursDuration;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Topic: ").append(name)
                .append(", hours_duration = ").append(hoursDuration);
        return builder.toString();
    }
}
