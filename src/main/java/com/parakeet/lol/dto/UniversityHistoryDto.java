package com.parakeet.lol.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UniversityHistoryDto {

    private State state;
    private String universityName;
    private LocalDate startDate;
    private LocalDate endDate;

}
