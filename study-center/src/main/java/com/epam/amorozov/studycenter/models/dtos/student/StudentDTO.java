package com.epam.amorozov.studycenter.models.dtos.student;

import com.epam.amorozov.studycenter.models.entities.Course;
import com.epam.amorozov.studycenter.models.entities.Score;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate startDate;

    private List<Course> courses;

    private List<Score> scores;

    private Double avgScore;

    public StudentDTO(StudentDTOBuilder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.startDate = builder.startDate;
        this.courses = builder.courses;
        this.scores = builder.scores;
        this.avgScore = builder().avgScore;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Student: ")
                .append(id).append(" ")
                .append(firstName).append(" ").append(lastName)
                .append(", start_date: ").append(startDate)
                .append(", courses: ").append(courses)
                .append(", scores: ").append(scores)
                .append(" , average: ").append(avgScore);
        return builder.toString();
    }
}
