package com.epam.amorozov.studycenter.repositories.jdbcimpl;

import com.epam.amorozov.studycenter.repositories.ScoreRepository;
import com.epam.amorozov.studycenter.utils.ResourceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@ConditionalOnClass(DataSource.class)
public class JdbcScoreRepository implements ScoreRepository {

    private static final String UPDATE_STUDENT_SCORE_BY_IDS_QUERY_PATH = "classpath:queries/score/update_student_score.sql";

    private final JdbcTemplate jdbcTemplate;
    private final ResourceReader resourceReader;

    @Autowired
    public JdbcScoreRepository(JdbcTemplate jdbcTemplate, ResourceReader resourceReader) {
        this.jdbcTemplate = jdbcTemplate;
        this.resourceReader = resourceReader;
    }

    @Override
    public boolean updateScoreById(Long studentId, Long topicId, int newStudentScore) {
        final String updateStudentScoreSql = resourceReader.readFileToString(UPDATE_STUDENT_SCORE_BY_IDS_QUERY_PATH);
        Object[] args = new Object[]{newStudentScore, topicId, studentId};
        return jdbcTemplate.update(updateStudentScoreSql, args) == 1;
    }
}
