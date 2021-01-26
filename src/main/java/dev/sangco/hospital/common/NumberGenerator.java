package dev.sangco.hospital.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class NumberGenerator {

    private final StringRedisTemplate redisTemplate;

    public String getPatientNumber() {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        String format = "00000";
        String year = Integer.toString(LocalDate.now().getYear());
        values.setIfAbsent(year, "0");
        Long increment = values.increment(year);
        // TODO setIfAbsent()를 해줬지만 null이 나온다면?
        //      그냥 exception을 던질까? DB에 붙어서 값을 가지고 올까?
        String generatedNumber = (increment == null) ? "" : Long.toString(increment);
        String number = year + format;
        String subNumber = number.substring(0, number.length() - generatedNumber.length());
        return subNumber + generatedNumber;
    }


}
