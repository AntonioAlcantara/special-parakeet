package com.parakeet.lol.mapper;

import com.parakeet.lol.dto.UniversityHistoryDto;
import com.parakeet.lol.model.StudentUniversityState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentUniversityStateMapper {

    @Mapping(target = "universityName", source = "sus.university.name")
    UniversityHistoryDto map(StudentUniversityState sus);

    List<UniversityHistoryDto> map(List<StudentUniversityState> susList);
}
