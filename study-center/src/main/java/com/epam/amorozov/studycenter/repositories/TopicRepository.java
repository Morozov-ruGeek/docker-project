package com.epam.amorozov.studycenter.repositories;

import com.epam.amorozov.studycenter.models.entities.Topic;

import java.util.List;

public interface TopicRepository {
    List<Long> saveTopicsInDB(List<Topic> topics);
}
