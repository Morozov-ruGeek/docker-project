package com.epam.amorozov.studycenter.services;

import com.epam.amorozov.studycenter.models.entities.Topic;

import java.util.List;

public interface TopicService {
    List<Long> saveTopicsInDb(List<Topic> topics);
}
