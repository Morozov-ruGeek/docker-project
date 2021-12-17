package com.epam.amorozov.studycenter.repositories;

import com.epam.amorozov.studycenter.models.entities.Course;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository {
    Course findCourseById(Long courseId);
    boolean saveNewCourseInDb(Course courses, List<Long> topicsId);
    List<Course> getAllCourses();
}
