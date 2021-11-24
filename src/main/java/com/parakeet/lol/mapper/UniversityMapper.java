package com.parakeet.lol.mapper;

import com.parakeet.lol.dto.UniversityModifierDto;
import com.parakeet.lol.dto.UniversitySearchResultDto;
import com.parakeet.lol.dto.UniversityWriterDto;
import com.parakeet.lol.model.University;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UniversityMapper {

    University map(UniversityWriterDto uwd);

    UniversitySearchResultDto map(University university);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    University updateUniversityFromDto(UniversityModifierDto dto, @MappingTarget University university);
}
