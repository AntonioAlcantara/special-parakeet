package com.parakeet.lol.controller;

import com.parakeet.lol.dto.StudentModifierDto;
import com.parakeet.lol.dto.StudentSearchResultDto;
import com.parakeet.lol.dto.StudentUniversityHistoryDto;
import com.parakeet.lol.dto.StudentWriterDto;
import com.parakeet.lol.exception.StudentExistsException;
import com.parakeet.lol.exception.StudentIsAlreadySubscribedException;
import com.parakeet.lol.exception.StudentNotExistsException;
import com.parakeet.lol.exception.UniversityNotExistsException;
import com.parakeet.lol.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@Api(tags = "Student")
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping("/insert")
    @ApiOperation("Insert new students")
    public ResponseEntity<Long> insertStudent(
            @RequestBody @Valid StudentWriterDto request
    ) throws UniversityNotExistsException, StudentExistsException {
        var studentId = service.insert(request);
        return ResponseEntity.ok(studentId);
    }

    @GetMapping("/findAll")
    @ApiOperation("Find all the students")
    public ResponseEntity<List<StudentSearchResultDto>> findAll(
            @ApiParam(value = "University Id for filter student search")
            @RequestParam(required = false) Long universityId,
            @ApiParam(value = "Page index")
            @RequestParam(required = false, defaultValue = "0") int pageIndex,
            @ApiParam(value = "Page size")
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        var result = service.findAll(
                Optional.ofNullable(universityId),
                pageIndex,
                pageSize
        );
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    @PutMapping("{studentId}/transfer/university/{universityId}")
    @ApiOperation("Transfer a student to another university")
    public ResponseEntity<Void> updateUserUniversity(
            @ApiParam(value = "Student Id to update", required = true)
            @PathVariable long studentId,
            @ApiParam(value = "New university", required = true)
            @PathVariable long universityId
    ) throws UniversityNotExistsException, StudentIsAlreadySubscribedException {
        service.updateUserUniversity(studentId, universityId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/info/{studentId}")
    @ApiOperation("Update student basic info")
    public ResponseEntity<Void> updateUserBasicInfo(
            @ApiParam(value = "Student Id to update", required = true)
            @PathVariable long studentId,
            @ApiParam(value = "Student basic info to update", required = true)
            @RequestBody StudentModifierDto studentModifierDto
    ) {
        service.updateUserInfo(studentId, studentModifierDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{studentId}")
    @ApiOperation("Delete the student info")
    public ResponseEntity<Void> deleteStudent(
            @ApiParam(value = "Student Id to delete", required = true)
            @PathVariable long studentId
    ) {
        var isNotDeleted = service.delete(studentId);
        return isNotDeleted ? new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) : ResponseEntity.ok().build();
    }

    @GetMapping("/find/history/{studentId}")
    @ApiOperation("find the student's college history")
    public ResponseEntity<StudentUniversityHistoryDto> findStudentCollegeHistory(
            @ApiParam(value = "Student to search")
            @PathVariable Long studentId
    ) throws StudentNotExistsException {
        var result = service.findStudentCollegeHistory(studentId);
        return ResponseEntity.ok(result);
    }
}
