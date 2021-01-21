package dev.sangco.hospital.repository;

import dev.sangco.hospital.web.dto.PatientQuerydslDto;
import dev.sangco.hospital.web.dto.PatientResponseDto;
import dev.sangco.hospital.web.dto.PatientSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientRepositoryCustom {

    Page<PatientQuerydslDto> search(PatientSearchCondition searchCondition, Pageable pageable);

}
