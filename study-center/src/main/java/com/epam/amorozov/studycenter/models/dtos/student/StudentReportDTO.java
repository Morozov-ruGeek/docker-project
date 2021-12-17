package com.epam.amorozov.studycenter.models.dtos.student;

import com.epam.amorozov.studycenter.models.entities.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentReportDTO {
    private String firstName;
    private String lastName;
    private List<Course> courses;
    private LocalDate startDate;
    private LocalDate currentDate;
    private double avgScore;

    public StudentReportDTO(StudentReportDTOBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.courses = builder.courses;
        this.startDate = builder.startDate;
        this.currentDate = builder.currentDate;
        this.avgScore = builder.avgScore;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Student: firstName - ").append(firstName)
                .append(", lastName - ").append(lastName)
                .append(", course - ").append(courses)
                .append(", startDate: ").append(startDate)
                .append(", currentDate: ").append(currentDate)
                .append(", avgScore = ").append(avgScore);
        return builder.toString();
    }
}
