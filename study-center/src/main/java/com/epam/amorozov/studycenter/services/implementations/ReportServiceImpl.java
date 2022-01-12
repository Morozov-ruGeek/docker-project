package com.epam.amorozov.studycenter.services.implementations;

import com.epam.amorozov.studycenter.models.dtos.student.StudentProgressDTO;
import com.epam.amorozov.studycenter.models.dtos.student.StudentReportDTO;
import com.epam.amorozov.studycenter.models.entities.Student;
import com.epam.amorozov.studycenter.services.ReportService;
import com.epam.amorozov.studycenter.services.StudentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    private static final String STUDY_SERVICE = "studyService";
    private final StudentService studentService;

    @Autowired
    public ReportServiceImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    @CircuitBreaker(name = STUDY_SERVICE)
    @Retry(name = STUDY_SERVICE)
    public String createStudentReport(Student student) {
        double studentAVGScore = studentService.getAVGScore(student);
        StudentReportDTO studentReportDTO = StudentReportDTO.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .courses(student.getCourses())
                .startDate(student.getStartDate())
                .currentDate(LocalDate.now())
                .avgScore(studentAVGScore)
                .build();
        return studentReportDTO.toString();
    }

    @Override
    @CircuitBreaker(name = STUDY_SERVICE)
    @Retry(name = STUDY_SERVICE)
    public boolean createTxtReport(List<String> listStringsStudentsReport) {
        final String fileNameForWriting = "students_report" + LocalDate.now() + ".txt";
        for (String stringFuture : listStringsStudentsReport) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileNameForWriting, true))) {
                bufferedWriter.write(stringFuture);
                bufferedWriter.newLine();
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    @CircuitBreaker(name = STUDY_SERVICE)
    @Retry(name = STUDY_SERVICE)
    public StudentProgressDTO progressReport(Long studentId) {
        Student student = studentService.findStudentById(studentId);
        double studentAVGScore = studentService.getAVGScore(student);
        return StudentProgressDTO.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .courses(student.getCourses())
                .scores(student.getScores())
                .avgScore(studentAVGScore)
                .build();
    }
}
