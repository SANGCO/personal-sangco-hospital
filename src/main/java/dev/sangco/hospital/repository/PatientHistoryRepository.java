package dev.sangco.hospital.repository;

import dev.sangco.hospital.domain.PatientHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientHistoryRepository extends JpaRepository<PatientHistory, Long> {
}
