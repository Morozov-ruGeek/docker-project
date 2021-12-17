package com.epam.amorozov.studycenter.services;

import com.epam.amorozov.studycenter.models.dtos.student.StudentProgressDTO;
import com.epam.amorozov.studycenter.models.entities.Student;

import java.util.List;

public interface ReportService {
    String createStudentReport(Student student);

    boolean createTxtReport(List<String> listStringsStudentsReport);

    StudentProgressDTO progressReport(Long studentId);
}
