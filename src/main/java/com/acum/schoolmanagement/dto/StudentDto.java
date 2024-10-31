package com.acum.schoolmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String fieldOfStudy;
    private List<CourseDto> courses = new ArrayList<>();

    public StudentDto(Long studentId, String firstName, String lastName, String email, String fieldOfStudy) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.fieldOfStudy = fieldOfStudy;
    }
}
