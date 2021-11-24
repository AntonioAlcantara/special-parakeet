package com.parakeet.lol.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentUniversityHistoryDto {

    private String name;
    private String lastname;
    private List<UniversityHistoryDto> universityHistoryDtoList;
}
