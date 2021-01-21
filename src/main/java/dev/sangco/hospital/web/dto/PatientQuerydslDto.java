package dev.sangco.hospital.web.dto;

import com.querydsl.core.annotations.QueryProjection;
import dev.sangco.hospital.domain.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    public PatientQuerydslDto(Long id, String name, String number, Gender gender, String birthdate, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.gender = gender.name();
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
    }

    public void setRecentVisit(String recentVisit) {
        this.recentVisit = recentVisit;
    }

}
