package com.epam.amorozov.studycenter.controllers;

import com.epam.amorozov.studycenter.models.dtos.course.CourseDTO;
import com.epam.amorozov.studycenter.models.dtos.course.NewCourseDTO;
import com.epam.amorozov.studycenter.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/all")
    public List<CourseDTO> getAllCourses(){
        return courseService.getAllCourses();
    }

    @PutMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNewCourse(@RequestBody NewCourseDTO newCourseDTO){
        courseService.addNewCourse(newCourseDTO);
    }

    @GetMapping("/{id}")
    public CourseDTO findCourseById(@PathVariable Long id){
        return courseService.getCourseById(id);
    }
}
