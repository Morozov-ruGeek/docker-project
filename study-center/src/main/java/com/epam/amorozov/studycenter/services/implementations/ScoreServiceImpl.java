package com.epam.amorozov.studycenter.services.implementations;

import com.epam.amorozov.studycenter.repositories.ScoreRepository;
import com.epam.amorozov.studycenter.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    public boolean updateScoreById(Long studentId, Long topicId, int newStudentScore) {
        return scoreRepository.updateScoreById(studentId, topicId, newStudentScore);
    }
}
