package com.epam.amorozov.studycenter.controllers;

import com.epam.amorozov.studycenter.models.dtos.student.NewStudentDTO;
import com.epam.amorozov.studycenter.models.dtos.student.StudentDTO;
import com.epam.amorozov.studycenter.models.entities.Student;
import com.epam.amorozov.studycenter.services.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private final StudentService studentService;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentController(StudentService studentService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNewStudent(@RequestBody NewStudentDTO newStudentDTO){
        studentService.addStudent(newStudentDTO);
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable Long id){
        return convertToStudentDTO(studentService.findStudentById(id));
    }

    @GetMapping("/all")
    public List<StudentDTO> getAllStudents(){
        return studentService.getAllStudents().stream()
                .map(this::convertToStudentDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/{studentId}/course/{courseId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void removeStudentFromCourse(@PathVariable Long studentId, @PathVariable Long courseId){
        studentService.removeStudentFromCourse(studentId, courseId);
    }

    @PutMapping("/rate/{studentId}/topic/{topicId}/score/{newScore}")
    @ResponseStatus(HttpStatus.CREATED)
    public void rateStudent(@PathVariable Long studentId, @PathVariable Long topicId, @PathVariable int newScore){
        studentService.rate(studentId, newScore, topicId);
    }

    @GetMapping("/deduct/{id}")
    public String studentPossibleDeduct(@PathVariable Long id){
        return studentService.possibleDeduct(id);
    }

    @PutMapping("/add/{studentId}/course/{courseId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addStudentOnCourse(@PathVariable Long studentId, @PathVariable Long courseId){
        studentService.addStudentOnCourse(studentId, courseId);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable Long id){
        studentService.deleteStudentById(id);
    }


    private StudentDTO convertToStudentDTO(Student student){
        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
        studentDTO.setAvgScore(studentService.getAVGScore(student));
        return studentDTO;
    }
}
