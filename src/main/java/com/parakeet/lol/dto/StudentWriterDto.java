package com.parakeet.lol.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
@EqualsAndHashCode(callSuper = true)
public class StudentWriterDto extends StudentDto {

    @Min(1L)
    @NotNull
    @ApiModelProperty(
            value = "University to join",
            example = "1",
            required = true
    )
    private long universityId;
}
