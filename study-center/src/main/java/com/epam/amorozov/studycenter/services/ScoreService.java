package com.epam.amorozov.studycenter.services;

public interface ScoreService {
    boolean updateScoreById(Long studentId, Long topicId, int newStudentScore);
}
