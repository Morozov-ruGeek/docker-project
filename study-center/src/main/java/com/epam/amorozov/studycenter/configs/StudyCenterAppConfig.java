package com.epam.amorozov.studycenter.configs;

import com.epam.amorozov.studycenter.models.entities.Course;
import com.epam.amorozov.studycenter.models.entities.Student;
import com.epam.amorozov.studycenter.models.entities.Topic;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class StudyCenterAppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

//    @Bean
    public Student demoStudent() {
        Student student = new Student();
        student.setFirstName("Vladimir");
        student.setLastName("Krasnoesolnishko");
        List<Topic> topics = new ArrayList<>(Arrays.asList(
                new Topic("structure", 6),
                new Topic("specifications", 8),
                new Topic("manufacture", 12),
                new Topic("thermodynamics", 14),
                new Topic("kinetics", 6)));
        Course course = new Course("materials_science", new ArrayList<>(topics));
        List<Course> courses = new ArrayList<>(List.of(course));
        student.setCourses(courses);
        student.setStartDate(LocalDate.parse("2021-09-03"));
        return student;
    }
}
