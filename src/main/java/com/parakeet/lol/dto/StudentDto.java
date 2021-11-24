package com.parakeet.lol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@ApiModel
public class StudentDto {


    @NotBlank
    @ApiModelProperty(
            value = "Student name",
            example = "Antonio",
            required = true
    )
    private String name;
    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}[0-9]{6}[A-Z]?$")
    @ApiModelProperty(
            value = "3 letters and 6 digits and optionally 1 letter (control character for authorities)",
            example = "ABC123456D",
            required = true
    )
    private String passportNumber;
    @NotBlank
    @ApiModelProperty(
            value = "Student lastname",
            example = "Alcántara",
            required = true
    )
    private String lastname;
    @Email(
            message = "Type a valid email",
            regexp = "^(.+)@(.+)$"
    )
    @ApiModelProperty(
            value = "Student email",
            example = "antonio@mail.com",
            required = true
    )
    @NotBlank
    private String email;
    @ApiModelProperty(
            value = "Student address",
            example = "Grove Street, Los Santos",
            required = true
    )
    @NotBlank
    private String address;
    @Past
    @NotNull
    @ApiModelProperty(
            value = "Date of birth (yyyy-mm-dd)",
            example = "1997-08-28",
            required = true
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
}
