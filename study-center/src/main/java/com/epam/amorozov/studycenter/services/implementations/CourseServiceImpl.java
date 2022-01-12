package com.epam.amorozov.studycenter.services.implementations;

import com.epam.amorozov.studycenter.exceptions.ResourceNotFoundException;
import com.epam.amorozov.studycenter.models.dtos.course.CourseDTO;
import com.epam.amorozov.studycenter.models.dtos.course.NewCourseDTO;
import com.epam.amorozov.studycenter.models.entities.Course;
import com.epam.amorozov.studycenter.models.entities.Topic;
import com.epam.amorozov.studycenter.repositories.CourseRepository;
import com.epam.amorozov.studycenter.services.CourseService;
import com.epam.amorozov.studycenter.services.TopicService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TopicService topicService;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, TopicService topicService) {
        this.courseRepository = courseRepository;
        this.topicService = topicService;
    }

    @Override
    public CourseDTO getCourseById(Long courseId) {
        Course course = courseRepository.findCourseById(courseId);
        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .topics(course.getTopics())
                .amount(course.getAmount())
                .build();
    }

    @Override
    public double getCourseDurationInDays(Course course) {
        final double workingHours = 8.0;
        int courseDurationInHours;
        courseDurationInHours = course.getTopics().stream().mapToInt(Topic::getHoursDuration).sum();
        return courseDurationInHours/workingHours;
    }

    @Override
    @Transactional
    public boolean addNewCourse(NewCourseDTO newCourseDTO) {
        Course newCourse = new Course();
        newCourse.setName(newCourse.getName());
        newCourse.setTopics(newCourseDTO.getTopics());
        newCourse.setAmount(newCourseDTO.getAmount());
        if(newCourse.getTopics().isEmpty()){
            log.debug("Course without topics");
            throw new ResourceNotFoundException("course should not be without topics");
        }
        List<Long> topicsId = topicService.saveTopicsInDb(newCourse.getTopics());
        return courseRepository.saveNewCourseInDb(newCourse, topicsId);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.getAllCourses().stream()
                .map(course -> CourseDTO.builder()
                        .id(course.getId())
                        .name(course.getName())
                        .topics(course.getTopics())
                        .amount(course.getAmount())
                        .build())
                .collect(Collectors.toList());
    }
}
