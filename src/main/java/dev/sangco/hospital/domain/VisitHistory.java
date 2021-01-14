package dev.sangco.hospital.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_history_id")
    private Long id;

    private Long formerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    private Long patientId;

    private LocalDateTime schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_history_id")
    private PatientHistory patientHistory;

    @Enumerated(EnumType.STRING)
    private State state;

    @Builder
    public VisitHistory(Long formerId, Hospital hospital, Long patientId, LocalDateTime schedule, PatientHistory patientHistory, State state) {
        this.formerId = formerId;
        this.hospital = hospital;
        this.patientId = patientId;
        this.schedule = schedule;
        this.patientHistory = patientHistory;
        this.state = state;
    }

    public static VisitHistory createVisitHistory(Visit visit) {
        return VisitHistory.builder()
                            .formerId(visit.getId())
                            .hospital(visit.getHospital())
                            .patientId(visit.getPatient().getId())
                            .schedule(visit.getSchedule())
                            .state(visit.getState())
                            .build();
    }

    public void setPatientHistory(PatientHistory patientHistory) {
        this.patientHistory = patientHistory;
    }
}
