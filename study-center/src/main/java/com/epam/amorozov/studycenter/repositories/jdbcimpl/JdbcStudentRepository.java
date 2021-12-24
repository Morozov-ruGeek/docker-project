package com.epam.amorozov.studycenter.repositories.jdbcimpl;

import com.epam.amorozov.studycenter.models.entities.Student;
import com.epam.amorozov.studycenter.repositories.StudentRepository;
import com.epam.amorozov.studycenter.utils.ResourceReader;
import com.epam.amorozov.studycenter.utils.extractors.StudentsExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@ConditionalOnClass(DataSource.class)
public class JdbcStudentRepository implements StudentRepository {

    private static final String GET_ALL_STUDENTS_SQL_QUERY_PATH = "classpath:queries/student/find_all_students.sql";
    private static final String GET_STUDENT_BY_ID_SQL_QUERY_PATH = "classpath:queries/student/find_student_by_id.sql";
    private static final String SAVE_NEW_STUDENT_SQL_QUERY_PATH = "classpath:queries/student/save_new_student.sql";
    private static final String LINK_STUDENT_WITH_COURSE_SQL_QUERY_PATH = "classpath:queries/student/link_student_with_course.sql";
    private static final String DELETE_STUDENT_BY_ID_SQL_QUERY_PATH = "classpath:queries/student/delete_student_by_id.sql";
    private static final String REMOVE_STUDENT_FROM_COURSE_SQL_QUERY_PATH = "classpath:queries/student/remove_student_from_course.sql";
    private static final String ADD_STUDENT_ON_COURSE_SQL_QUERY_PATH = "classpath:queries/student/add_student_on_course.sql";

    private final JdbcTemplate jdbcTemplate;
    private final GeneratedKeyHolder keyHolder;
    private final ResourceReader resourceReader;
    private final StudentsExtractor studentsExtractor;

    @Autowired
    public JdbcStudentRepository(JdbcTemplate jdbcTemplate, GeneratedKeyHolder keyHolder, ResourceReader resourceReader, StudentsExtractor studentsExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.keyHolder = keyHolder;
        this.resourceReader = resourceReader;
        this.studentsExtractor = studentsExtractor;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        String getAllStudentsSql = resourceReader.readFileToString(GET_ALL_STUDENTS_SQL_QUERY_PATH);
        return jdbcTemplate.query(getAllStudentsSql, studentsExtractor);
    }

    @Override
    public boolean saveStudent(Student student, List<Long> courseIds) {
        String saveStudentsSql = resourceReader.readFileToString(SAVE_NEW_STUDENT_SQL_QUERY_PATH);
        boolean checkOne = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(saveStudentsSql, new String[]{"id"});
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setDate(3, Date.valueOf(student.getStartDate()));
            return preparedStatement;
        }, keyHolder) == 1;
        long newUserId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        student.setId(newUserId);

        if (courseIds.size() != 0) {
            linkCoursesToStudent(newUserId, courseIds);
        }
        return checkOne;
    }

    @Override
    public boolean deleteStudentById(Long id) {
        String deleteStudentByIdSql = resourceReader.readFileToString(DELETE_STUDENT_BY_ID_SQL_QUERY_PATH);
        Object[] args = new Object[]{id};
        return jdbcTemplate.update(deleteStudentByIdSql, args) == 1;
    }

    @Override
    public boolean removeStudentsFromCourse(Long studentId, Long courseId) {
        String removeStudentFromCourseSql = resourceReader.readFileToString(REMOVE_STUDENT_FROM_COURSE_SQL_QUERY_PATH);
        Object[] args = new Object[]{studentId, courseId};
        return jdbcTemplate.update(removeStudentFromCourseSql, args) == 1;
    }

    @Override
    public boolean addStudentInCourse(long studentId, long courseId) {
        String addStudentInCourseSql = resourceReader.readFileToString(ADD_STUDENT_ON_COURSE_SQL_QUERY_PATH);
        Object[] args = new Object[]{studentId, courseId};
        return jdbcTemplate.update(addStudentInCourseSql, args) == 1;
    }

    @Override
    public Student findStudentById(Long studentId) {
        String getStudentByIdSql = resourceReader.readFileToString(GET_STUDENT_BY_ID_SQL_QUERY_PATH);
        List<Student> students = jdbcTemplate.query(getStudentByIdSql, studentsExtractor, studentId);
        if (students.isEmpty()) {
            new Student();
        }
        return students.get(0);
    }

    private boolean linkCoursesToStudent(Long studentId, List<Long> courseIds) {
        String linkStudentWithCourseSql = resourceReader.readFileToString(LINK_STUDENT_WITH_COURSE_SQL_QUERY_PATH);
        List<Object[]> batch = courseIds.stream().map(topicId -> new Object[]{studentId, topicId}).collect(Collectors.toList());
        return jdbcTemplate.batchUpdate(linkStudentWithCourseSql, batch).length == courseIds.size();
    }
}
