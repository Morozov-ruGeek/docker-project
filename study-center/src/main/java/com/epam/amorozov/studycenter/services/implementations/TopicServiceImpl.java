package com.epam.amorozov.studycenter.services.implementations;

import com.epam.amorozov.studycenter.models.entities.Topic;
import com.epam.amorozov.studycenter.repositories.TopicRepository;
import com.epam.amorozov.studycenter.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public List<Long> saveTopicsInDb(List<Topic> topics) {
        return topicRepository.saveTopicsInDB(topics);
    }
}
