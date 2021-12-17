package com.epam.amorozov.studycenter.services;

import com.epam.amorozov.studycenter.models.dtos.student.NewStudentDTO;
import com.epam.amorozov.studycenter.models.entities.Student;

import java.util.List;

public interface StudentService {

    boolean addStudent(NewStudentDTO newStudentDTO);

    boolean removeStudentFromCourse(Long studentId, Long courseId);

    boolean rate(Long studentId, int newScore, Long topicId);

    Double daysRemain(Student student);

    String possibleDeduct(Long studentId);

    Double getAVGScore(Student student);

    List<Student> getAllStudents();

    Student findStudentById(Long studentId);

    boolean addStudentOnCourse(long studentId, long courseId);

    boolean deleteStudentById(Long id);
}
