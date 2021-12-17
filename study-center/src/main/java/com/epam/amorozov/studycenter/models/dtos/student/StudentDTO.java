package com.epam.amorozov.studycenter.models.dtos.student;

import com.epam.amorozov.studycenter.models.entities.Course;
import com.epam.amorozov.studycenter.models.entities.Score;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@ApiModel(description = "Student dto in the application.")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {

    @ApiModelProperty(notes = "Unique identifier of the student. No two students can have the same id.", example = "1", required = true, position = 0)
    private Long id;

    @ApiModelProperty(notes = "First name of the student.", example = "Harry", required = true, position = 1)
    private String firstName;

    @ApiModelProperty(notes = "Last name of the student.", example = "Potter", required = true, position = 1)
    private String lastName;

    @ApiModelProperty(notes = "Start date of studying (y-m-d)", example = "2021-12-16", required = true, position = 3)
    private LocalDate startDate;

    @ApiModelProperty(notes = "List courses where student is study", required = true, position = 4)
    private List<Course> courses;

    @ApiModelProperty(notes = "Student scores list", required = true, position = 5)
    private List<Score> scores;

    @ApiModelProperty(notes = "Average mark for all courses", example = "65.85", required = true, position = 6)
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
