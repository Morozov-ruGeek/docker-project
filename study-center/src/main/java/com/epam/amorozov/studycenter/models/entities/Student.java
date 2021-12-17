package com.epam.amorozov.studycenter.models.entities;


import com.epam.amorozov.studycenter.annotations.InjectRandomMark;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private LocalDate startDate;
    private List<Course> courses;
    private List<Score> scores;

    public Student(String firstName, String lastName, List<Course> courses, LocalDate startDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = courses;
        this.startDate = startDate;
    }

    @InjectRandomMark(maxValue = 100)
    public void setScores(List<Score> scores){
        this.scores = scores;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addScore(Score score){
        scores.add(score);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Student: ").append(firstName).append(" ").append(lastName)
                .append(", start_date: ").append(startDate)
                .append(", courses: ").append(courses)
                .append(", scores: ").append(scores);
        return builder.toString();
    }
}
