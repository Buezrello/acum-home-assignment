package com.acum.schoolmanagement.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LectureSummary {
    private Long id;
    private String lecturerName;
    private String fieldOfStudy;
}
