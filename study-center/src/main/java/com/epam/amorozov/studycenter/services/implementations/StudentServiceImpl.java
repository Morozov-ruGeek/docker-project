package com.epam.amorozov.studycenter.services.implementations;

import com.epam.amorozov.studycenter.models.dtos.student.NewStudentDTO;
import com.epam.amorozov.studycenter.models.entities.Student;
import com.epam.amorozov.studycenter.repositories.StudentRepository;
import com.epam.amorozov.studycenter.services.CourseService;
import com.epam.amorozov.studycenter.services.PaymentEntityService;
import com.epam.amorozov.studycenter.services.ScoreService;
import com.epam.amorozov.studycenter.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final int maximumScore = 100;

    private final StudentRepository studentRepository;
    private final CourseService courseService;
    private final ScoreService scoreService;
    private final PaymentEntityService paymentEntityService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              CourseService courseService,
                              ScoreService scoreService,
                              PaymentEntityService paymentEntityService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
        this.scoreService = scoreService;
        this.paymentEntityService = paymentEntityService;
    }

    @Override
    @Transactional
    public boolean addStudent(NewStudentDTO newStudentDTO) {
        Student student = new Student();
        student.setFirstName(newStudentDTO.getFirstName());
        student.setLastName(newStudentDTO.getLastName());
        student.setStartDate(student.getStartDate());
        student.setCourses(new ArrayList<>());
        student.setScores(new ArrayList<>());

        List<Long> courseIds = new ArrayList<>();
        return studentRepository.saveStudent(student, courseIds);
    }

    @Override
    public boolean removeStudentFromCourse(Long studentId, Long courseId) {
        return studentRepository.removeStudentsFromCourse(studentId, courseId);
    }

    @Override
    public boolean rate(Long studentId, int newScore, Long topicId) {
        int minimumScore = 0;
        if (newScore <= minimumScore || newScore > maximumScore) {
            log.error("{} over limits. Must be {}..{}", newScore, minimumScore, maximumScore);
        }
        if (studentRepository.findStudentById(studentId) == null) {
            throw new NoSuchElementException("Student not found");
        } else return scoreService.updateScoreById(studentId, topicId, newScore);
    }

    @Override
    public Double daysRemain(Student student) {
        final int workingHours = 8;
        double courseDuration = student.getCourses().stream()
                .mapToDouble(courseService::getCourseDurationInDays)
                .sum();
        int daysGone = (int) (DAYS.between(student.getStartDate(), LocalDate.now()) - 1);
        double hoursGone = daysGone * workingHours;
        if (courseDuration - hoursGone < 0) {
            return 0.0;
        } else return courseDuration - hoursGone;
    }

    @Override
    public String possibleDeduct(Long studentId) {
        Student student = studentRepository.findStudentById(studentId);
        double possibleAvgMark = (getAVGScore(student) + maximumScore) / 2;
        int minimumPassingScore = 75;
        return possibleAvgMark > minimumPassingScore ? student.getFirstName() + " " + student.getLastName() + " have a chance to end course" : student.getFirstName() + " " + student.getLastName() + " have chance to DEDUCT";
    }

    @Override
    public Double getAVGScore(Student student) {
        double studentAvgScore = student.getScores().stream()
                .mapToDouble(score -> score.getScore().doubleValue())
                .average()
                .orElse(0.0);
        return new BigDecimal(studentAvgScore).setScale(1, RoundingMode.UP).doubleValue();
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    @Override
    public Student findStudentById(Long studentId) {
        return studentRepository.findStudentById(studentId);
    }

    @Override
    public boolean addStudentOnCourse(long studentId, long courseId) {
        paymentEntityService.saveNewPayment(studentId, courseId);
        return studentRepository.addStudentInCourse(studentId, courseId);
    }

    @Override
    public boolean deleteStudentById(Long id) {
        return studentRepository.deleteStudentById(id);
    }
}
