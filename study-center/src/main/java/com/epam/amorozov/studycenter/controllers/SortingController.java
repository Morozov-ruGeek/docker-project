package com.epam.amorozov.studycenter.controllers;

import com.epam.amorozov.studycenter.models.dtos.student.StudentDTO;
import com.epam.amorozov.studycenter.services.SortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sorts")
public class SortingController {

    private final SortingService sortingService;

    @Autowired
    public SortingController(SortingService sortingService) {
        this.sortingService = sortingService;
    }

    @GetMapping("/days_to_end")
    public List<StudentDTO> getStudentsSortingByDaysToEnd(){
        return sortingService.sortByDaysToEnd();
    }

    @GetMapping("/by_avg_scores")
    public List<StudentDTO> getStudentsSortingByAVGScore(){
        return sortingService.sortByAVGScore();
    }

    @GetMapping("/not_be_deduct")
    public List<StudentDTO> getStudentsSortingByChanceNotBeDeducted(){
        return sortingService.sortByChanceNotBeDeducted();
    }
}
