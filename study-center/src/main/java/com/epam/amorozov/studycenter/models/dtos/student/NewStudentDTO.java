package com.epam.amorozov.studycenter.models.dtos.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewStudentDTO {

    private String firstName;

    private String lastName;

    private LocalDate startDate;

    public NewStudentDTO(NewStudentDTOBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.startDate = builder.startDate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Student: ").append(firstName).append(" ").append(lastName)
                .append(", start_date: ").append(startDate);
        return builder.toString();
    }
}
