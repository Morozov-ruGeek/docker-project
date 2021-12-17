package com.epam.amorozov.studycenter.multithread_workers;

import com.epam.amorozov.studycenter.models.entities.Student;
import com.epam.amorozov.studycenter.services.ReportService;
import com.epam.amorozov.studycenter.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
@Slf4j
public class MultithreadingWorker {

    private final StudentService studentService;
    private final ReportService reportService;

    @Autowired
    public MultithreadingWorker(StudentService studentService, ReportService reportService) {
        this.studentService = studentService;
        this.reportService = reportService;
    }

    public boolean createReportAllStudents() {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(studentService.getAllStudents().size());
            List<Callable<String>> callableStudentsReports = new ArrayList<>();
            for (Student iterStudent : studentService.getAllStudents()) {
                callableStudentsReports.add(()-> String.valueOf(reportService.createStudentReport(iterStudent)));
            }
            List<Future<String>> futuresStudentsReports = executorService.invokeAll(callableStudentsReports);
            List<String> studentsReports = new ArrayList<>();
            for (Future<String> futuresStudentsReport : futuresStudentsReports) {
                String report = futuresStudentsReport.get();
                studentsReports.add(report);
            }
            executorService.awaitTermination(15, TimeUnit.SECONDS);
            executorService.shutdown();
            if (!studentsReports.isEmpty()) {
                reportService.createTxtReport(studentsReports);
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
