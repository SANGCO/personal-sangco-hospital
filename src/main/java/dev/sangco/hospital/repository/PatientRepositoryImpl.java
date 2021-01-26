package dev.sangco.hospital.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.sangco.hospital.web.dto.PatientQuerydslDto;
import dev.sangco.hospital.web.dto.PatientSearchCondition;
import dev.sangco.hospital.web.dto.QPatientQuerydslDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static dev.sangco.hospital.domain.QPatient.patient;
import static dev.sangco.hospital.domain.QVisit.visit;
import static org.springframework.util.ObjectUtils.isEmpty;

@AllArgsConstructor
public class PatientRepositoryImpl implements PatientRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PatientQuerydslDto> search(PatientSearchCondition searchCondition, Pageable pageable) {
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        List<Tuple> visits = queryFactory
                .select(visit.patient.id, visit.schedule.max())
                .from(visit)
                .join(visit.patient, patient)
                .where(nameEq(searchCondition.getName()),
                        numberEq(searchCondition.getNumber()),
                        birthdateEq(searchCondition.getBirthdate()))
                .groupBy(visit.patient.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

//        List<Long> ids = dtoList.stream().map(PatientQuerydslDto::getId).collect(Collectors.toList());
//        List<Tuple> visits = queryFactory
//                .select(visit.patient.id, visit.schedule.max())
//                .from(visit)
//                .where(visit.patient.id.in(ids))
//                .groupBy(visit.patient.id)
//                .fetch();

        for (int i = 0; i < dtoList.size(); i++) {
            PatientQuerydslDto patientQuerydslDto = dtoList.get(i);
            if (visits.size() - 1 >= i) {
                patientQuerydslDto.setRecentVisit(Objects.requireNonNull(visits.get(i).get(visit.schedule.max())).toString());
            }
        }

        long total = queryFactory
                .select(patient)
                .from(patient)
                .where(nameEq(searchCondition.getName()),
                        numberEq(searchCondition.getNumber()),
                        birthdateEq(searchCondition.getBirthdate()))
                .fetchCount();
        return new PageImpl<>(dtoList, pageable, total);
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

}
