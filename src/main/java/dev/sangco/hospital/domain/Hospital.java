package dev.sangco.hospital.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    private Long id;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String number;

    @Column(length = 10, nullable = false)
    private String director;

    @Builder
    public Hospital(String name, String number, String director) {
        this.name = name;
        this.number = number;
        this.director = director;
    }

}
