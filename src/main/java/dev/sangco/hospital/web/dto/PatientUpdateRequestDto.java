package dev.sangco.hospital.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PatientUpdateRequestDto {

    @NotBlank
    private String phoneNumber;

}
