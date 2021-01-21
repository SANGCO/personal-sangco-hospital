package dev.sangco.hospital.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PatientSearchCondition {
    private String name;
    private String number;
    private String birthdate;

    @Builder
    public PatientSearchCondition(String name, String number, String birthdate) {
        this.name = name;
        this.number = number;
        this.birthdate = birthdate;
    }
}
