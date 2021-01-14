package dev.sangco.hospital.repository;

import dev.sangco.hospital.web.dto.PatientQuerydslDto;
import dev.sangco.hospital.web.dto.PatientSearchCondition;

import java.util.List;

public interface PatientRepositoryCustom {

    List<PatientQuerydslDto> search(PatientSearchCondition searchCondition);

}
