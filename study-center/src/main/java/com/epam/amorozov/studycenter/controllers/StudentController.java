package com.epam.amorozov.studycenter.controllers;

import com.epam.amorozov.studycenter.models.dtos.student.NewStudentDTO;
import com.epam.amorozov.studycenter.models.dtos.student.StudentDTO;
import com.epam.amorozov.studycenter.models.entities.Student;
import com.epam.amorozov.studycenter.services.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/students")
@Api("Set of endpoints for students")
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
    @ApiOperation("Create new student")
    public void saveNewStudent(@RequestBody NewStudentDTO newStudentDTO){
        studentService.addStudent(newStudentDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Returns a student dto by their identifier.")
    public StudentDTO getStudentById(@ApiParam("Id of the book to be obtained. Cannot be empty.") @PathVariable Long id){
        return convertToStudentDTO(studentService.findStudentById(id));
    }

    @GetMapping("/all")
    @ApiOperation("Return list student dtos")
    public List<StudentDTO> getAllStudents(){
        return studentService.getAllStudents().stream()
                .map(this::convertToStudentDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/{studentId}/course/{courseId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Remove student by their identifier from course by their identifier")
    public void removeStudentFromCourse(@ApiParam("Id of the student to be obtained. Cannot be empty.") @PathVariable Long studentId,
                                        @ApiParam("Id of the course to be obtained. Cannot be empty.") @PathVariable Long courseId){
        studentService.removeStudentFromCourse(studentId, courseId);
    }

    @PutMapping("/rate/{studentId}/topic/{topicId}/score/{newScore}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Rate student by their identifier, topic identifier and score")
    public void rateStudent(@ApiParam("Id of the student to be obtained. Cannot be empty.") @PathVariable Long studentId,
                            @ApiParam("Id of the topic to be obtained. Cannot be empty.") @PathVariable Long topicId,
                            @ApiParam("Score to be obtained. Cannot be empty. Min = 0, Max = 100.") @PathVariable int newScore){
        studentService.rate(studentId, newScore, topicId);
    }

    @GetMapping("/deduct/{id}")
    @ApiOperation("Returns a string with information about the possibility of student expulsion")
    public String studentPossibleDeduct(@ApiParam("Id of the student to be obtained. Cannot be empty.") @PathVariable Long id){
        return studentService.possibleDeduct(id);
    }

    @PutMapping("/add/{studentId}/course/{courseId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Adding a student to a course")
    public void addStudentOnCourse(@ApiParam("Id of the student to be obtained. Cannot be empty.") @PathVariable Long studentId,
                                   @ApiParam("Id of the course to be obtained. Cannot be empty.") @PathVariable Long courseId){
        studentService.addStudentOnCourse(studentId, courseId);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Removing a student from the database")
    public void deleteStudentById(@ApiParam("Id of the student to be obtained. Cannot be empty.") @PathVariable Long id){
        studentService.deleteStudentById(id);
    }


    private StudentDTO convertToStudentDTO(Student student){
        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
        studentDTO.setAvgScore(studentService.getAVGScore(student));
        return studentDTO;
    }
}
