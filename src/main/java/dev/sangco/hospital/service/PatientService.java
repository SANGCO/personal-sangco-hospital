package dev.sangco.hospital.service;

import dev.sangco.hospital.web.dto.PatientCreateRequestDto;
import dev.sangco.hospital.web.dto.PatientResponseDto;
import dev.sangco.hospital.web.dto.PatientUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {

    PatientResponseDto save(PatientCreateRequestDto requestDto);

    void update(Long id, PatientUpdateRequestDto requestDto);

    void delete(Long id);

    PatientResponseDto findById(Long id);

    Page<PatientResponseDto> findAll(Pageable pageable);

}
