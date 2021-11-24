package com.parakeet.lol.controller;

import com.parakeet.lol.dto.UniversityModifierDto;
import com.parakeet.lol.dto.UniversitySearchResultDto;
import com.parakeet.lol.dto.UniversityWriterDto;
import com.parakeet.lol.exception.UniversityNotExistsException;
import com.parakeet.lol.service.UniversityService;
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

@Validated
@RestController
@Api(tags = "University")
@RequestMapping("/university")
public class UniversityController {

    @Autowired
    private UniversityService uService;

    @PostMapping("/insert")
    @ApiOperation("Insert new universities")
    public ResponseEntity<Long> insert(
            @ApiParam(value = "Model for insert new universities", required = true)
            @RequestBody @Valid UniversityWriterDto request
    ) {
        var universityId = uService.insert(request);
        return ResponseEntity.ok(universityId);
    }

    @GetMapping("/findAll")
    @ApiOperation("Find all the universities")
    public ResponseEntity<List<UniversitySearchResultDto>> findAll(
            @ApiParam(value = "Page index")
            @RequestParam(required = false, defaultValue = "0") int pageIndex,
            @ApiParam(value = "Page size")
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        var result = uService.findAll(pageIndex, pageSize);
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    @PutMapping("/update/{universityId}")
    @ApiOperation("Update the university info")
    public ResponseEntity<Void> updateUniversity(
            @ApiParam(value = "University Id to update", required = true)
            @PathVariable long universityId,
            @ApiParam(value = "University basic info to update", required = true)
            @RequestBody UniversityModifierDto universityModifierDto
    ) throws UniversityNotExistsException {
        uService.updateUniversityInfo(universityId, universityModifierDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{universityId}")
    @ApiOperation("Delete a university")
    public ResponseEntity<Void> deleteUniversity(
            @ApiParam(value = "University Id to delete", required = true)
            @PathVariable long universityId
    ) {
        var isNotDeleted = uService.delete(universityId);
        return isNotDeleted ? new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) : ResponseEntity.ok().build();
    }
}
