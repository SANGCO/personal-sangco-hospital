package dev.sangco.hospital.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.sangco.hospital.web.dto.PatientQuerydslDto;
import dev.sangco.hospital.web.dto.PatientSearchCondition;
import dev.sangco.hospital.web.dto.QPatientQuerydslDto;
import lombok.AllArgsConstructor;

import java.util.List;

import static dev.sangco.hospital.domain.QPatient.patient;
import static dev.sangco.hospital.domain.QVisit.visit;
import static org.springframework.util.ObjectUtils.isEmpty;

@AllArgsConstructor
public class PatientRepositoryImpl implements PatientRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PatientQuerydslDto> search(PatientSearchCondition searchCondition) {
        return queryFactory
                .select(
                        new QPatientQuerydslDto(
                        patient.id,
                        patient.name,
                        patient.number,
                        patient.gender,
                        patient.birthdate,
                        patient.phoneNumber,
                        ExpressionUtils.as(
                                JPAExpressions.select(visit.schedule.max()).from(visit).leftJoin(visit.patient, patient).groupBy(visit.patient.id), "recentVisit"
                        )))
                // TODO from 절에 서브쿼리 쓸 일이 없을라나?
                .from(patient)
                .where(nameEq(searchCondition.getName()),
                        numberEq(searchCondition.getNumber()),
                        birthdateEq(searchCondition.getBirthdate()))
                .fetch();
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
