package dev.sangco.hospital.repository;

import dev.sangco.hospital.domain.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long>, PatientRepositoryCustom {

    Page<Patient> findAll(Pageable pageable);

}
