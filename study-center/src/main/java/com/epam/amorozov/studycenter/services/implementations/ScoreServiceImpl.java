package com.epam.amorozov.studycenter.services.implementations;

import com.epam.amorozov.studycenter.repositories.ScoreRepository;
import com.epam.amorozov.studycenter.services.ScoreService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl implements ScoreService {

    private static final String STUDY_SERVICE = "studyService";
    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    @CircuitBreaker(name = STUDY_SERVICE)
    @Retry(name = STUDY_SERVICE)
    public boolean updateScoreById(Long studentId, Long topicId, int newStudentScore) {
        return scoreRepository.updateScoreById(studentId, topicId, newStudentScore);
    }
}
