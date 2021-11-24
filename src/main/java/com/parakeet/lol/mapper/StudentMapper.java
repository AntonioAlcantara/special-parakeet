package com.parakeet.lol.mapper;

import com.parakeet.lol.dto.StudentModifierDto;
import com.parakeet.lol.dto.StudentSearchResultDto;
import com.parakeet.lol.dto.StudentUniversityHistoryDto;
import com.parakeet.lol.dto.StudentWriterDto;
import com.parakeet.lol.model.Student;
import com.parakeet.lol.model.StudentUniversityState;
import org.mapstruct.*;

import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring", uses = StudentUniversityStateMapper.class)
public interface StudentMapper {

    Student map(StudentWriterDto swd);

    StudentSearchResultDto map(Student student);

    List<StudentSearchResultDto> map(List<Student> student);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    Student updateStudentFromDto(StudentModifierDto dto, @MappingTarget Student student);

    @AfterMapping
    default void getStateAndUniversity(Student student, @MappingTarget StudentSearchResultDto ssrd) {
        var sus = student.getStudentUniversityStateList();
        if (sus != null && !sus.isEmpty()) {
            // Prioritize States with null end date
            var currentSusNullEndDate = sus.parallelStream()
                    .filter(value -> value.getEndDate() == null)
                    .max(
                            Comparator.comparing(
                                    StudentUniversityState::getCreationDate
                            )
                    );

            if (currentSusNullEndDate.isPresent()) {
                ssrd.setState(currentSusNullEndDate.get().getState());
                var university = currentSusNullEndDate.get().getUniversity();
                ssrd.setUniversityName(
                        university != null ? university.getName() : ""
                );
            } else {
                var currentSus = sus.parallelStream()
                        .filter(value -> value.getEndDate() != null)
                        .max(
                                Comparator.comparing(
                                        StudentUniversityState::getCreationDate
                                )
                        ).orElseThrow();

                ssrd.setState(currentSus.getState());

                var university = currentSus.getUniversity();
                ssrd.setUniversityName(
                        university != null ? university.getName() : ""
                );
            }
        }
    }

    @Mapping(target = "universityHistoryDtoList", source = "student.studentUniversityStateList")
    StudentUniversityHistoryDto entityToSuhDto(Student student);
}
