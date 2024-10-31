package com.acum.schoolmanagement.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateStudentDto {
    private String firstName;
    private String lastName;
    private String email;
    private String fieldOfStudy;
    private List<Long> courseIds;
}
