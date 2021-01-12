package dev.sangco.hospital.web;

import dev.sangco.hospital.common.ErrorResponse;
import dev.sangco.hospital.domain.Patient;
import dev.sangco.hospital.service.PatientService;
import dev.sangco.hospital.web.dto.PatientCreateRequestDto;
import dev.sangco.hospital.web.dto.PatientResponseDto;
import dev.sangco.hospital.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody @Valid PatientCreateRequestDto requestDto, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new Result<ErrorResponse>(ErrorResponse.createErrorResponse(bindingResult)));
        }
        Patient patient = patientService.save(requestDto);
        PatientResponseDto responseDto = new PatientResponseDto(patient);
        return ResponseEntity.created(URI.create(String.format("/patients/%s", patient.getId()))).body(new Result<PatientResponseDto>(responseDto));
    }

}
