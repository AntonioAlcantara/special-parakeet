package com.parakeet.lol.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UniversityModifierDto {

    @ApiModelProperty(
            example = "University of Vienna"
    )
    private String name;
    @ApiModelProperty(
            example = "The University was founded on 12 March 1365 by Rudolf IV, Duke of Austria"
    )
    private String description;
    @ApiModelProperty(
            example = "Universitätsring 1, 1010 Wien, Austria"
    )
    private String address;
}
