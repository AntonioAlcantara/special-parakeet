package com.parakeet.lol.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@ApiModel
public class StudentModifierDto {

    @ApiModelProperty(
            value = "Student name",
            example = "Antonio"
    )
    private String name;
    @Pattern(regexp = "^[A-Z]{3}[0-9]{6}[A-Z]?$")
    @ApiModelProperty(
            value = "3 letters and 6 digits and optionally 1 letter (control character for authorities)",
            example = "ABC123456D"
    )
    private String passportNumber;
    @ApiModelProperty(
            value = "Student lastname",
            example = "Alcántara"
    )
    private String lastname;
    @Email(
            message = "Type a valid email",
            regexp = "^(.+)@(.+)$"
    )
    @ApiModelProperty(
            value = "Student email",
            example = "antonio@mail.com"
    )
    private String email;
    @ApiModelProperty(
            value = "Student address",
            example = "Grove Street, Los Santos"
    )
    private String address;
    @Past
    @ApiModelProperty(
            value = "Date of birth (yyyy-mm-dd)",
            example = "1997-08-28"
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
}
