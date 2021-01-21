package dev.sangco.hospital.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PatientSearchResponseDto {
    private String name;
    private String number;
    private String gender;
    private String birthdate;
    private String phoneNumber;
    private String recentVisit;

    @Builder
    public PatientSearchResponseDto(String name, String number, String gender, String birthdate, String phoneNumber, String recentVisit) {
        this.name = name;
        this.number = number;
        this.gender = gender;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.recentVisit = recentVisit;
    }
}
