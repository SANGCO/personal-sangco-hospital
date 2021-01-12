package dev.sangco.hospital.repository;

import dev.sangco.hospital.domain.Hospital;
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
class HospitalRepositoryTest {

    @Autowired
    HospitalRepository hospitalRepository;

    @Test
    public void test() {
        // Given
        Hospital hospital = Hospital.builder()
                .name("테스트 병원")
                .number("010-0000-0000")
                .director("김아무개").build();
        Hospital savedHospital = hospitalRepository.save(hospital);

        // When
        Optional<Hospital> findHospital = hospitalRepository.findById(savedHospital.getId());

        // Then
        assertTrue(findHospital.isPresent());
        assertEquals(findHospital.get(), hospital);
    }

}