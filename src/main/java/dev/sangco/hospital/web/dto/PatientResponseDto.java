package dev.sangco.hospital.web.dto;

import dev.sangco.hospital.domain.Patient;
import dev.sangco.hospital.domain.Visit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class PatientResponseDto {
    private Long id;
    private String hospitalName;
    // TODO 여기서는 필요없겠네 지우자
    private String recentVisit;
    private String name;
    private String number;
    private String gender;
    private String birthdate;
    private String phoneNumber;
    private List<VisitDto> visits;

    public PatientResponseDto(Patient patient) {
        this.id = patient.getId();
        this.hospitalName = patient.getHospital().getName();
        this.name = patient.getName();
        this.number = patient.getNumber();
        this.gender = patient.getGender().name();
        this.birthdate = patient.getBirthdate();
        this.phoneNumber = patient.getPhoneNumber();
        this.visits = patient.getVisits().stream()
                .map(VisitDto::new).collect(Collectors.toList());
        if (!visits.isEmpty()) {
            this.recentVisit = visits.get(0).getSchedule().toString();
        }
    }

    @Getter
    private static class VisitDto {
        private String hospitalName;
        private String schedule;
        private String state;

        public VisitDto(Visit visit) {
            this.hospitalName = visit.getHospital().getName();
            this.schedule = visit.getSchedule().toString();
            this.state = visit.getState().name();
        }
    }
}
