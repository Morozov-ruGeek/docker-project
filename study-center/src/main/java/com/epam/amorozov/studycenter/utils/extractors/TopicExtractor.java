package com.epam.amorozov.studycenter.utils.extractors;

import com.epam.amorozov.studycenter.models.entities.Topic;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TopicExtractor implements ResultSetExtractor<Topic> {
    private final static String TOPIC_ID_COLUMN = "topic_id";
    private final static String TOPIC_NAME_COLUMN = "topic_name";
    private final static String TOPIC_HOURS_DURATIONS_COLUMN = "topic_hours_durations";

    @Override
    public Topic extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Topic topic = new Topic();
        topic.setId(resultSet.getLong(TOPIC_ID_COLUMN));
        topic.setName(resultSet.getString(TOPIC_NAME_COLUMN));
        topic.setHoursDuration(resultSet.getInt(TOPIC_HOURS_DURATIONS_COLUMN));
        return topic;
    }
}
