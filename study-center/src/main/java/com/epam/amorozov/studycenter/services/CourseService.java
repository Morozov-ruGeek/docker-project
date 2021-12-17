package com.epam.amorozov.studycenter.services;

import com.epam.amorozov.studycenter.models.dtos.course.CourseDTO;
import com.epam.amorozov.studycenter.models.dtos.course.NewCourseDTO;
import com.epam.amorozov.studycenter.models.entities.Course;

import java.util.List;

public interface CourseService {
    CourseDTO getCourseById(Long courseId);
    double getCourseDurationInDays(Course course);
    boolean addNewCourse(NewCourseDTO newCourseDTO);
    List<CourseDTO> getAllCourses();
}
