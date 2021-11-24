package com.parakeet.lol.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class UniversityDto {

    @NotBlank
    @ApiModelProperty(
            example = "University of Vienna",
            required = true
    )
    private String name;
    @NotBlank
    @ApiModelProperty(
            example = "The University was founded on 12 March 1365 by Rudolf IV, Duke of Austria",
            required = true
    )
    private String description;
    @NotBlank
    @ApiModelProperty(
            example = "Universitätsring 1, 1010 Wien, Austria",
            required = true
    )
    private String address;
}
