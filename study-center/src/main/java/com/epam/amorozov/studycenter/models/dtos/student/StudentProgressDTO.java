package com.epam.amorozov.studycenter.models.dtos.student;

import com.epam.amorozov.studycenter.models.entities.Course;
import com.epam.amorozov.studycenter.models.entities.Score;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProgressDTO {

    private String firstName;
    private String lastName;
    private List<Course> courses;
    private List<Score> scores;
    private Double avgScore;

    public StudentProgressDTO(StudentProgressDTOBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.courses = builder.courses;
        this.scores = builder.scores;
        this.avgScore = builder.avgScore;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Progress Report: ")
                .append("Student: ").append(firstName).append(" ").append(lastName)
                .append("\nCourses: ").append(courses)
                .append("\nScores: ")
                .append(scores)
                .append("\nAverage score: ").append(avgScore);
        return builder.toString();
    }
}
