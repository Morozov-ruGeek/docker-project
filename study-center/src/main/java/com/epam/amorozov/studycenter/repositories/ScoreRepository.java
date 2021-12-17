package com.epam.amorozov.studycenter.repositories;

public interface ScoreRepository {
    boolean updateScoreById(Long studentId, Long topicId, int newStudentScore);
}
