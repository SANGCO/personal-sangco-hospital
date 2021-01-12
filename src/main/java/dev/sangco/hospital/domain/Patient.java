package dev.sangco.hospital.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Patient extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 13, nullable = false)
    private String number;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 10, nullable = false)
    private String birthdate;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

}
