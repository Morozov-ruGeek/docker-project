package com.epam.amorozov.studycenter.services.implementations;

import com.epam.amorozov.studycenter.models.dtos.student.StudentDTO;
import com.epam.amorozov.studycenter.services.SortingService;
import com.epam.amorozov.studycenter.services.StudentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SortingServiceImpl implements SortingService {

    private static final String STUDY_SERVICE = "studyService";
    private final StudentService studentService;

    @Autowired
    public SortingServiceImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    @CircuitBreaker(name = STUDY_SERVICE)
    @Retry(name = STUDY_SERVICE)
    public List<StudentDTO> sortByDaysToEnd() {
        return studentService.getAllStudents().stream()
                .sorted(Collections.reverseOrder((s1, s2) -> (int) (studentService.daysRemain(s1) - studentService.daysRemain(s2))))
                .map(student -> StudentDTO.builder()
                        .id(student.getId())
                        .firstName(student.getFirstName())
                        .lastName(student.getLastName())
                        .startDate(student.getStartDate())
                        .courses(student.getCourses())
                        .scores(student.getScores())
                        .avgScore(studentService.getAVGScore(student))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @CircuitBreaker(name = STUDY_SERVICE)
    @Retry(name = STUDY_SERVICE)
    public List<StudentDTO> sortByAVGScore() {
        return studentService.getAllStudents().stream()
                .sorted(Collections.reverseOrder(Comparator.comparingDouble(studentService::getAVGScore)))
                .map(student -> StudentDTO.builder()
                        .id(student.getId())
                        .firstName(student.getFirstName())
                        .lastName(student.getLastName())
                        .startDate(student.getStartDate())
                        .courses(student.getCourses())
                        .scores(student.getScores())
                        .avgScore(studentService.getAVGScore(student))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @CircuitBreaker(name = STUDY_SERVICE)
    @Retry(name = STUDY_SERVICE)
    public List<StudentDTO> sortByChanceNotBeDeducted() {
        final int maximumScore = 100;
        return studentService.getAllStudents().stream()
                .sorted(Collections.reverseOrder(Comparator.comparingDouble(s -> (studentService.getAVGScore(s) + maximumScore) / 2.0)))
                .map(student -> StudentDTO.builder()
                        .id(student.getId())
                        .firstName(student.getFirstName())
                        .lastName(student.getLastName())
                        .startDate(student.getStartDate())
                        .courses(student.getCourses())
                        .scores(student.getScores())
                        .avgScore(studentService.getAVGScore(student))
                        .build())
                .collect(Collectors.toList());
    }
}
