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
public class Visit extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(nullable = false)
    private LocalDateTime schedule;

    @Enumerated(EnumType.STRING)
    private State state;

    @Builder
    public Visit(Hospital hospital, Patient patient, LocalDateTime schedule, State state) {
        this.hospital = hospital;
        this.patient = patient;
        this.schedule = schedule;
        this.state = state;
    }

}
