package com.epam.amorozov.studycenter.repositories.jdbcimpl;

import com.epam.amorozov.studycenter.models.entities.Topic;
import com.epam.amorozov.studycenter.repositories.TopicRepository;
import com.epam.amorozov.studycenter.utils.IdKeyHolder;
import com.epam.amorozov.studycenter.utils.ResourceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@ConditionalOnClass(DataSource.class)
public class JdbcTopicRepository implements TopicRepository {

    private static final String SAVE_TOPIC_SQL_QUERY_PATH = "classpath:queries/topic/save_topic.sql";

    private final JdbcTemplate jdbcTemplate;
    private final IdKeyHolder idKeyHolder;
    private final ResourceReader resourceReader;

    @Autowired
    public JdbcTopicRepository(JdbcTemplate jdbcTemplate, IdKeyHolder idKeyHolder, ResourceReader resourceReader) {
        this.jdbcTemplate = jdbcTemplate;
        this.idKeyHolder = idKeyHolder;
        this.resourceReader = resourceReader;
    }

    @Override
    public List<Long> saveTopicsInDB(List<Topic> topics) {
        final String saveTopicSql = resourceReader.readFileToString(SAVE_TOPIC_SQL_QUERY_PATH);
        List<Long> ids = new ArrayList<>();
        topics.forEach(topic -> {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(saveTopicSql, new String[]{"ID"});
                preparedStatement.setString(1, topic.getName());
                preparedStatement.setInt(2, topic.getHoursDuration());
                return preparedStatement;
            }, idKeyHolder);
            ids.add(Objects.requireNonNull(idKeyHolder.getKey()).longValue());
        });
        return ids;
    }
}
