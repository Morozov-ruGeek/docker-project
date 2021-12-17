package com.epam.amorozov.studycenter.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    private Long id;
    private Topic topic;
    private Integer score;

    public Score(Topic topic, Integer score) {
        this.topic = topic;
        this.score = score;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Score: ")
                .append(topic.getName()).append(" - ")
                .append(score);
        return builder.toString();
    }
}
