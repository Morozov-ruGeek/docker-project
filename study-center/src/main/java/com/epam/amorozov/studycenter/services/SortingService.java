package com.epam.amorozov.studycenter.services;

import com.epam.amorozov.studycenter.models.dtos.student.StudentDTO;

import java.util.List;

public interface SortingService {

    List<StudentDTO> sortByDaysToEnd();

    List<StudentDTO> sortByAVGScore();

    List<StudentDTO> sortByChanceNotBeDeducted();
}
