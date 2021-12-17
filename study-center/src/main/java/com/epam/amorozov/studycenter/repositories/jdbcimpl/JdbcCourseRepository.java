package com.epam.amorozov.studycenter.repositories.jdbcimpl;

import com.epam.amorozov.studycenter.models.entities.Course;
import com.epam.amorozov.studycenter.repositories.CourseRepository;
import com.epam.amorozov.studycenter.utils.IdKeyHolder;
import com.epam.amorozov.studycenter.utils.ResourceReader;
import com.epam.amorozov.studycenter.utils.extractors.CourseExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@ConditionalOnClass(DataSource.class)
public class JdbcCourseRepository implements CourseRepository {

    private final static String FIND_COURSE_BY_ID_SQL_QUERY_PATH = "classpath:queries/course/find_course_by_id.sql";
    private final static String SAVE_NEW_COURSE_SQL_QUERY_PATH = "classpath:queries/course/save_new_course.sql";
    private final static String LINK_COURSE_WITH_TOPIC_SQL_QUERY_PATH = "classpath:queries/course/link_course_with_topic.sql";
    private final static String FIND_ALL_COURSES_QUERY_PATH = "classpath:queries/course/find_all_courses.sql";


    private final JdbcTemplate jdbcTemplate;
    private final IdKeyHolder idKeyHolder;
    private final ResourceReader resourceReader;
    private final CourseExtractor courseExtractor;

    @Autowired
    public JdbcCourseRepository(JdbcTemplate jdbcTemplate, IdKeyHolder idKeyHolder, ResourceReader resourceReader, CourseExtractor courseExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.idKeyHolder = idKeyHolder;
        this.resourceReader = resourceReader;
        this.courseExtractor = courseExtractor;
    }

    @Override
    public Course findCourseById(Long courseId) {
        final String findCourseByIdSql = resourceReader.readFileToString(FIND_COURSE_BY_ID_SQL_QUERY_PATH);
        return jdbcTemplate.query(findCourseByIdSql, courseExtractor, courseId).get(0);
    }

    @Override
    public boolean saveNewCourseInDb(Course course, List<Long> topicsId) {
        final String saveNewCourseSql = resourceReader.readFileToString(SAVE_NEW_COURSE_SQL_QUERY_PATH);
        boolean sqlAddingCourseCheck = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(saveNewCourseSql, new String[]{"ID"});
            preparedStatement.setString(1, course.getName());
            preparedStatement.setBigDecimal(2, course.getAmount());
            return preparedStatement;
        }, idKeyHolder) == 1;
        long newCourseId = Objects.requireNonNull(idKeyHolder.getKey()).longValue();
        course.setId(newCourseId);

        linkTopicsToCourse(newCourseId, topicsId);

        return sqlAddingCourseCheck;
    }

    @Override
    public List<Course> getAllCourses() {
        final String getAllCoursesSql = resourceReader.readFileToString(FIND_ALL_COURSES_QUERY_PATH);
        return jdbcTemplate.query(getAllCoursesSql, courseExtractor);
    }

    private boolean linkTopicsToCourse(Long newCourseId, List<Long> topicIds){
        final String linkCourseWithTopicsSql = resourceReader.readFileToString(LINK_COURSE_WITH_TOPIC_SQL_QUERY_PATH);
        List<Object[]> batch = topicIds.stream().map(topicId -> new Object[]{newCourseId, topicId}).collect(Collectors.toList());
        return jdbcTemplate.batchUpdate(linkCourseWithTopicsSql, batch).length == topicIds.size();
    }
}
