package dev.sangco.hospital.service;

import dev.sangco.hospital.domain.Hospital;
import dev.sangco.hospital.domain.Patient;
import dev.sangco.hospital.domain.PatientHistory;
import dev.sangco.hospital.repository.HospitalRepository;
import dev.sangco.hospital.repository.PatientHistoryRepository;
import dev.sangco.hospital.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientHistoryRepository patientHistoryRepository;

    @Test
    public void test() {
        // Given

        // When
        patientService.delete(1L);

        // Then
        Optional<Patient> patient = patientRepository.findById(1L);
        assertFalse(patient.isPresent());
        Optional<PatientHistory> patientHistory = patientHistoryRepository.findById(1L);
        assertTrue(patientHistory.isPresent());
    }

}