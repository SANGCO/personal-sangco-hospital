package dev.sangco.hospital.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sangco.hospital.domain.Gender;
import dev.sangco.hospital.web.dto.PatientCreateRequestDto;
import dev.sangco.hospital.web.dto.PatientUpdateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Patient 생성 테스트")
    public void savePatientTest() throws Exception {
        PatientCreateRequestDto requestDto = PatientCreateRequestDto.builder()
                .hospitalName("테스트 병원")
                .name("테스트환자")
                .gender(Gender.FEMALE.name())
                .birthdate("1988-01-01")
                .phoneNumber("010-0000-0000")
                .build();

        mockMvc.perform(post("/patients")
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(LOCATION))
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("data.id").value(11));
    }

    @Test
    @DisplayName("Patient 생성 실패 테스트")
    public void savePatientFailTest() throws Exception {
        PatientCreateRequestDto requestDto = PatientCreateRequestDto.builder().build();

        mockMvc.perform(post("/patients")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("fieldErrors").isArray());
    }

    @Test
    @DisplayName("Patient 수정 테스트")
    public void updatePatientTest() throws Exception {
        PatientUpdateRequestDto requestDto = new PatientUpdateRequestDto("010-9999-9999");

        mockMvc.perform(put("/patients/1")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Patient 수정 실패 테스트")
    public void updatePatientFailTest() throws Exception {
        PatientUpdateRequestDto requestDto = new PatientUpdateRequestDto("");

        mockMvc.perform(put("/patients/1")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("fieldErrors[0].field").value("phoneNumber"));
    }

    @Test
    @DisplayName("Patient 삭제 테스트")
    public void deletePatientTest() throws Exception {
        mockMvc.perform(delete("/patients/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Patient 삭제 실패 테스트")
    public void deletePatientFailTest() throws Exception {
        mockMvc.perform(delete("/patients/1000"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("code").value("IllegalArgumentException"));
    }

    @Test
    @DisplayName("Patient 조회 테스트")
    public void findPatientTest() throws Exception {
        mockMvc.perform(get("/patients/10")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("data.id").value(10))
                .andExpect(jsonPath("data.visits").isArray());
    }

    @Test
    @DisplayName("Patient 조회 실패 테스트")
    public void findPatientFailTest() throws Exception {
        mockMvc.perform(get("/patients/1000")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("code").value("IllegalArgumentException"));
    }

    @Test
    @DisplayName("Patient List 조회 테스트")
    public void findPatientListTest() throws Exception {
        mockMvc.perform(get("/patients?page=0&size=10")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("content").isArray());
    }

}