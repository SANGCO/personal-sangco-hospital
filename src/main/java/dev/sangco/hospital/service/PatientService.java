package dev.sangco.hospital.service;

import dev.sangco.hospital.domain.Gender;
import dev.sangco.hospital.domain.Hospital;
import dev.sangco.hospital.domain.Patient;
import dev.sangco.hospital.repository.HospitalRepository;
import dev.sangco.hospital.repository.PatientRepository;
import dev.sangco.hospital.repository.VisitRepository;
import dev.sangco.hospital.web.dto.PatientCreateRequestDto;
import dev.sangco.hospital.web.dto.PatientUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PatientService {

    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;
    private final VisitRepository visitRepository;

    @Transactional
    public Patient save(PatientCreateRequestDto requestDto) {
        Hospital hospital = hospitalRepository.findByName(requestDto.getHospitalName())
                .orElseThrow(IllegalArgumentException::new);
        Patient patient = Patient.builder()
                .hospital(hospital)
                .name(requestDto.getName())
                .gender(Gender.valueOf(requestDto.getGender()))
                .birthdate(requestDto.getBirthdate())
                .phoneNumber(requestDto.getPhoneNumber())
                .build();
        return patientRepository.save(patient);
    }

    @Transactional
    public void update(Long id, PatientUpdateRequestDto requestDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 사용자가 없습니다. ID = " + id));
        patient.update(requestDto.getPhoneNumber());
    }

    @Transactional
    public void delete(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 사용자가 없습니다. ID = " + id));
        patientRepository.delete(patient);
    }

}
