package dev.sangco.hospital.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Visit> visits = new ArrayList<>();

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 13, unique = true, nullable = false)
    private String number = generateNumber();

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 10)
    private String birthdate;

    @Column(length = 20)
    private String phoneNumber;

    @Builder
    public Patient(Hospital hospital, String name, Gender gender, String birthdate, String phoneNumber) {
        this.hospital = hospital;
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
    }

    private String generateNumber() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return String.valueOf(Math.abs(random.nextInt()));
    }

    public void update(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
