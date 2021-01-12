package dev.sangco.hospital.repository;

import dev.sangco.hospital.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
