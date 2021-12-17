package com.epam.amorozov.studycenter.models.dtos.student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@ApiModel(description = "New student dto in the application.")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewStudentDTO {

    @ApiModelProperty(notes = "First name of the student.", example = "Harry", required = true, position = 0)
    private String firstName;

    @ApiModelProperty(notes = "Last name of the student.", example = "Potter", required = true, position = 1)
    private String lastName;

    @ApiModelProperty(notes = "Start date of studying (y-m-d)", example = "2021-08-25", required = true, position = 2)
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
