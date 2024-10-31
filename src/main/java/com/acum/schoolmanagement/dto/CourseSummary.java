package com.acum.schoolmanagement.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseSummary {
    private Long courseId;
    private String courseName;
    private int hours;
    private int maxStudents;
    private int studentCount;
    private List<LectureSummary> lectures = new ArrayList<>();

    public CourseSummary(Long courseId, String courseName, int hours, int maxStudents, int studentCount) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.hours = hours;
        this.maxStudents = maxStudents;
        this.studentCount = studentCount;
    }

    public void addLecture(LectureSummary lecture) {
        this.lectures.add(lecture);
    }
}
