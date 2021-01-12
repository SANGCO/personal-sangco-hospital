package dev.sangco.hospital.repository;

import dev.sangco.hospital.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class VisitRepositoryTest {

    @Autowired
    VisitRepository visitRepository;

    @Test
    public void test() {
        // Given
        Hospital hospital = Hospital.builder()
                .name("테스트 병원")
                .number("010-0000-0000")
                .director("김아무개").build();

        Patient patient = Patient.builder()
                .hospital(hospital)
                .name("테스트환자")
                .number("00000000")
                .gender(Gender.MALE)
                .birthdate("1988-01-01")
                .phoneNumber("010-0000-0000").build();

        Visit visit = Visit.builder()
                .hospital(hospital)
                .patient(patient)
                .schedule(LocalDateTime.now())
                .state(State.END).build();

        Visit savedVisit = visitRepository.save(visit);

        // When
        Optional<Visit> findVisit = visitRepository.findById(savedVisit.getId());

        // Then
        assertTrue(findVisit.isPresent());
        assertEquals(findVisit.get(), visit);
    }

}