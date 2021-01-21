package dev.sangco.hospital.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.sangco.hospital.domain.Gender;
import dev.sangco.hospital.domain.Hospital;
import dev.sangco.hospital.domain.Patient;
import dev.sangco.hospital.web.dto.PatientQuerydslDto;
import dev.sangco.hospital.web.dto.PatientSearchCondition;
import dev.sangco.hospital.web.dto.PatientSearchResponseDto;
import dev.sangco.hospital.web.dto.QPatientQuerydslDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static dev.sangco.hospital.domain.QPatient.patient;
import static dev.sangco.hospital.domain.QVisit.visit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.ObjectUtils.isEmpty;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Test
    public void test() {
        // Given
        Hospital hospital = Hospital.builder()
                .name("테스트 병원")
                .number("010-0000-0000")
                .director("김아무개").build();

        Patient patient = Patient.builder()
                .hospital(hospital)
                .name("테스트환자")
                .gender(Gender.MALE)
                .birthdate("1988-01-01")
                .phoneNumber("010-0000-0000").build();

        Patient savedPatient = patientRepository.save(patient);

        // When
        Optional<Patient> findPatient = patientRepository.findById(savedPatient.getId());

        // Then
        assertTrue(findPatient.isPresent());
        assertEquals(findPatient.get(), patient);
    }

    @Test
    public void searchTest() {
        PatientSearchCondition searchCondition = PatientSearchCondition
                .builder().build();

        List<PatientQuerydslDto> dtoList = queryFactory
            .select(new QPatientQuerydslDto(
                patient.id,
                patient.name,
                patient.number,
                patient.gender,
                patient.birthdate,
                patient.phoneNumber))
            .from(patient)
            .where(nameEq(searchCondition.getName()),
                    numberEq(searchCondition.getNumber()),
                    birthdateEq(searchCondition.getBirthdate()))
            .fetch();

        List<Tuple> visits = queryFactory
            .select(visit.patient.id, visit.schedule.max())
            .from(visit)
            .join(visit.patient, patient)
            .where(nameEq(searchCondition.getName()),
                    numberEq(searchCondition.getNumber()),
                    birthdateEq(searchCondition.getBirthdate()))
            .groupBy(patient.id)
            .fetch();

        for (int i = 0; i < dtoList.size(); i++) {
            PatientQuerydslDto patientQuerydslDto = dtoList.get(i);
            patientQuerydslDto.setRecentVisit(Objects.requireNonNull(visits.get(i).get(visit.schedule.max())).toString());
        }



        System.out.println();
    }

    private BooleanExpression nameEq(String name) {
        return isEmpty(name) ? null : patient.name.eq(name);
    }

    private BooleanExpression numberEq(String number) {
        return isEmpty(number) ? null : patient.number.eq(number);
    }

    private BooleanExpression birthdateEq(String birthdate) {
        return isEmpty(birthdate) ? null : patient.birthdate.eq(birthdate);
    }

//    @Test
//    public void searchNameTest() {
//        // Given
//        PatientSearchCondition patientSearchCondition = PatientSearchCondition.builder()
//                .name("테스트환자1")
//                .build();
//
//        // When
//        List<PatientQuerydslDto> search = patientRepository.search(patientSearchCondition);
//
//        // Then
//        assertEquals(1, search.size());
//    }
//
//    @Test
//    public void searchBirthdateTest() {
//        // Given
//        PatientSearchCondition patientSearchCondition = PatientSearchCondition.builder()
//                .birthdate("1988-01-01")
//                .build();
//
//        // When
//        List<PatientQuerydslDto> search = patientRepository.search(patientSearchCondition);
//
//        // Then
//        assertEquals(1, search.size());
//        System.out.println(search.size());
//    }

}