package com.epam.amorozov.studycenter.utils.extractors;

import com.epam.amorozov.studycenter.models.entities.Course;
import com.epam.amorozov.studycenter.models.entities.Score;
import com.epam.amorozov.studycenter.models.entities.Student;
import com.epam.amorozov.studycenter.models.entities.Topic;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudentsExtractor implements ResultSetExtractor<List<Student>> {

    private static final String STUDENT_ID_COLUMN = "student_id";
    private static final String STUDENT_FIRST_NAME_COLUMN = "first_name";
    private static final String STUDENT_LAST_NAME_COLUMN = "last_name";
    private static final String START_DATE_COLUMN = "start_date";
    private static final String COURSE_ID_COLUMN = "course_id";
    private static final String COURSE_NAME_COLUMN = "course_name";
    private static final String COURSE_AMOUNT_COLUMN = "course_amount";
    private static final String TOPIC_ID_COLUMN = "topic_id";
    private static final String TOPIC_NAME_COLUMN = "topic_name";
    private static final String TOPIC_HOURS_DURATIONS_COLUMN = "topic_hours_durations";
    private static final String SCORE_ID_COLUMN = "score_id";
    private static final String TOPIC_SCORE_COLUMN = "topic_score";


    @Override
    public List<Student> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, Student> studentsMap = new HashMap<>();
        Map<Long, Course> coursesMap = new HashMap<>();
        Map<Long, Topic> topicsMap = new HashMap<>();
        while (resultSet.next()) {
            long studentId = resultSet.getLong(STUDENT_ID_COLUMN);
            Student student = studentsMap.get(studentId);
            if (student == null) {
                student = new Student();
                student.setId(resultSet.getLong(STUDENT_ID_COLUMN));
                student.setFirstName(resultSet.getString(STUDENT_FIRST_NAME_COLUMN));
                student.setLastName(resultSet.getString(STUDENT_LAST_NAME_COLUMN));
                student.setStartDate(LocalDate.parse(resultSet.getString(START_DATE_COLUMN)));
                student.setCourses(new ArrayList<>());
                student.setScores(new ArrayList<>());
            }

            long courseId = resultSet.getLong(COURSE_ID_COLUMN);
            Course course = coursesMap.get(courseId);
            if (course == null) {
                course = new Course();
                course.setId(courseId);
                course.setName(resultSet.getString(COURSE_NAME_COLUMN));
                course.setAmount(resultSet.getBigDecimal(COURSE_AMOUNT_COLUMN));
                course.setTopics(new ArrayList<>());
                coursesMap.put(courseId, course);
                student.addCourse(course);
            }

            long topicId = resultSet.getLong(TOPIC_ID_COLUMN);
            Topic topic = topicsMap.get(topicId);
            if (topic == null) {
                topic = new Topic();
                topic.setId(topicId);
                topic.setName(resultSet.getString(TOPIC_NAME_COLUMN));
                topic.setHoursDuration(resultSet.getInt(TOPIC_HOURS_DURATIONS_COLUMN));
                topicsMap.put(topicId, topic);
                course.addTopic(topic);
            }

            Score score = new Score();
            score.setId(resultSet.getLong(SCORE_ID_COLUMN));
            score.setTopic(topic);
            score.setScore(resultSet.getInt(TOPIC_SCORE_COLUMN));
            student.addScore(score);

            studentsMap.put(studentId, student);
        }
        return new ArrayList<>(studentsMap.values());
    }
}
