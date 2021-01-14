package dev.sangco.hospital.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatientHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_history_id")
    private Long id;

    private Long formerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToMany(mappedBy = "patientHistory", cascade = CascadeType.ALL)
    private List<VisitHistory> visitHistories = new ArrayList<>();

    @Column(length = 45)
    private String name;

    @Column(length = 13)
    private String number;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 10)
    private String birthdate;

    @Column(length = 20)
    private String phoneNumber;

    @Builder
    public PatientHistory(Long formerId, Hospital hospital, List<VisitHistory> visitHistories, String name, String number, Gender gender, String birthdate, String phoneNumber) {
        this.formerId = formerId;
        this.hospital = hospital;
        this.visitHistories = visitHistories;
        this.name = name;
        this.number = number;
        this.gender = gender;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
    }

    public static PatientHistory createPatientHistory(Patient patient) {
        PatientHistory patientHistory = PatientHistory
            .builder()
                .formerId(patient.getId())
                .hospital(patient.getHospital())
                .visitHistories(patient.getVisits().stream()
                        .map(VisitHistory::createVisitHistory).collect(Collectors.toList()))
                .name(patient.getName())
                .number(patient.getNumber())
                .gender(patient.getGender())
                .birthdate(patient.getBirthdate())
                .phoneNumber(patient.getPhoneNumber())
                .build();
        patientHistory.getVisitHistories().forEach(v -> v.setPatientHistory(patientHistory));
        return patientHistory;
    }

}
