package com.parakeet.lol.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UniversitySearchResultDto extends UniversityDto {

    private long id;
}
