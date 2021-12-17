package com.epam.amorozov.studycenter.utils.extractors;

import com.epam.amorozov.studycenter.models.entities.Course;
import com.epam.amorozov.studycenter.models.entities.Topic;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CourseExtractor implements ResultSetExtractor<List<Course>> {
    private static final String COURSE_ID_COLUMN = "course_id";
    private static final String COURSE_NAME_COLUMN = "course_name";
    private static final String COURSE_AMOUNT_COLUMN = "course_amount";
    private static final String TOPIC_ID_COLUMN = "topic_id";
    private static final String TOPIC_NAME_COLUMN = "topic_name";
    private static final String TOPIC_HOURS_DURATIONS_COLUMN = "topic_hours_durations";

    @Override
    public List<Course> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, Course> map = new HashMap<>();
        while (resultSet.next()) {
            long courseId = resultSet.getLong(COURSE_ID_COLUMN);
            Course course = map.get(courseId);
            if (course == null) {
                course = new Course();
                course.setId(courseId);
                course.setName(resultSet.getString(COURSE_NAME_COLUMN));
                course.setAmount(resultSet.getBigDecimal(COURSE_AMOUNT_COLUMN));
                course.setTopics(new ArrayList<>());
                map.put(courseId, course);
            }
            long topicId = resultSet.getLong(TOPIC_ID_COLUMN);
            List<Topic> topics = new ArrayList<>();
            if (topicId > 0) {
                Topic topic = new Topic();
                topic.setId(topicId);
                topic.setName(resultSet.getString(TOPIC_NAME_COLUMN));
                topic.setHoursDuration(resultSet.getInt(TOPIC_HOURS_DURATIONS_COLUMN));
                topics.add(topic);
                course.addTopic(topic);
            }
        }
        return new ArrayList<>(map.values());
    }
}
