package com.epam.amorozov.studycenter.repositories;

import com.epam.amorozov.studycenter.models.entities.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository {

    List<Student> getAllStudents();

    boolean saveStudent(Student student, List<Long> courseIds);

    boolean deleteStudentById(Long id);

    boolean removeStudentsFromCourse(Long studentId, Long courseId);

    boolean addStudentInCourse(long studentId, long courseId);

    Student findStudentById(Long id);
}
