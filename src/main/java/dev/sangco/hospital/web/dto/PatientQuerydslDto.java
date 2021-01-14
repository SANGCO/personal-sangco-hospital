package dev.sangco.hospital.web.dto;

import com.querydsl.core.annotations.QueryProjection;
import dev.sangco.hospital.domain.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PatientQuerydslDto {
    private Long id;
    private String name;
    private String number;
    private String gender;
    private String birthdate;
    private String phoneNumber;
    private String recentVisit;

    @QueryProjection
    public PatientQuerydslDto(Long id, String name, String number, Gender gender, String birthdate, String phoneNumber, LocalDateTime recentVisit) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.gender = gender.name();
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.recentVisit = recentVisit.toString();
    }
}
