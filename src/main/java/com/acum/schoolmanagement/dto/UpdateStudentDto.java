package com.acum.schoolmanagement.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateStudentDto {
    private String firstName;
    private String lastName;
    @Email(message = "Email should be valid")
    private String email;
    private String fieldOfStudy;
    private List<Long> courseIds;
}
