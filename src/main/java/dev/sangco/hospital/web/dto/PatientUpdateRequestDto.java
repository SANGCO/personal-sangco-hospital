package dev.sangco.hospital.web.dto;

import dev.sangco.hospital.domain.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
