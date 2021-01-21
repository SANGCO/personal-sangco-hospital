package dev.sangco.hospital.service;

import dev.sangco.hospital.web.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {

    PatientResponseDto save(PatientCreateRequestDto requestDto);

    void update(Long id, PatientUpdateRequestDto requestDto);

    void delete(Long id);

    PatientResponseDto findById(Long id);

    Page<PatientResponseDto> findAll(Pageable pageable);

    Page<PatientQuerydslDto> search(PatientSearchCondition searchCondition, Pageable pageable);

}
