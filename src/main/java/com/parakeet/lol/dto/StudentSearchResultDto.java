package com.parakeet.lol.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel
@EqualsAndHashCode(callSuper = true)
public class StudentSearchResultDto extends StudentDto {

    private long id;
    private String universityName;
    private State state;
}
