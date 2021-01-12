package dev.sangco.hospital.web.dto;

import dev.sangco.hospital.domain.Patient;
import dev.sangco.hospital.domain.Visit;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class PatientResponseDto {
    private Long id;
    private String hospitalName;
    private String recentVisit;
    private String name;
    private String number;
    private String gender;
    private String birthdate;
    private String phoneNumber;

    public PatientResponseDto(Patient patient) {
        this.id = patient.getId();
        this.hospitalName = patient.getHospital().getName();
        List<Visit> visits = patient.getVisits();
        if (!visits.isEmpty()) {
            this.recentVisit = visits.get(0).getSchedule().toString();
        }
        this.name = patient.getName();
        this.number = patient.getNumber();
        this.gender = patient.getGender().name();
        this.birthdate = patient.getBirthdate();
        this.phoneNumber = patient.getPhoneNumber();
    }
}
