package dev.sangco.hospital.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PatientCreateRequestDto {

    @NotBlank
    private String hospitalName;
    @NotBlank
    private String name;
    @NotBlank
    private String number;
    @NotBlank
    private String gender;
    @NotBlank
    private String birthdate;
    @NotBlank
    private String phoneNumber;

}
