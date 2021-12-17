package com.epam.amorozov.studycenter.controllers;

import com.epam.amorozov.studycenter.models.dtos.student.StudentProgressDTO;
import com.epam.amorozov.studycenter.multithread_workers.MultithreadingWorker;
import com.epam.amorozov.studycenter.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;
    private final MultithreadingWorker multithreadingWorker;

    @Autowired
    public ReportController(ReportService reportService, MultithreadingWorker multithreadingWorker) {
        this.reportService = reportService;
        this.multithreadingWorker = multithreadingWorker;
    }

    @GetMapping("/{id}")
    public StudentProgressDTO createStudentProgressReport(@PathVariable Long id){
        return reportService.progressReport(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAllStudentsTxtReport(){
        multithreadingWorker.createReportAllStudents();
    }
}
