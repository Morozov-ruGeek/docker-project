package com.epam.amorozov.studycenter.annotations;

import com.epam.amorozov.studycenter.models.entities.Score;
import com.epam.amorozov.studycenter.models.entities.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class InjectRandomMarkAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            InjectRandomMark annotation = method.getAnnotation(InjectRandomMark.class);
            if (annotation != null) {
                int minScore = annotation.minValue();
                int maxScore = annotation.maxValue();
                List<Score> scores = new ArrayList<>();
                Student student = (Student) bean;
                for (Score score : student.getScores()) {
                    if (ThreadLocalRandom.current().nextInt(minScore, maxScore) < maxScore || ThreadLocalRandom.current().nextInt(minScore, maxScore) > minScore) {
                        Score randomScore = new Score(score.getId() ,score.getTopic(), ThreadLocalRandom.current().nextInt(minScore, maxScore));
                        scores.add(scores.indexOf(score), randomScore);
                    } else {
                        log.error("Out of bound: {}", student.getCourses().get(student.getScores().indexOf(score)));
                    }
                }
                ReflectionUtils.invokeMethod(method, bean, scores);
                break;
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
