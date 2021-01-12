package dev.sangco.hospital.repository;

import dev.sangco.hospital.domain.Gender;
import dev.sangco.hospital.domain.Hospital;
import dev.sangco.hospital.domain.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class PatientRepositoryTest {

    @Autowired
    PatientRepository patientRepository;

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

        Patient savedPatient = patientRepository.save(patient);

        // When
        Optional<Patient> findPatient = patientRepository.findById(savedPatient.getId());

        // Then
        assertTrue(findPatient.isPresent());
        assertEquals(findPatient.get(), patient);
    }

}