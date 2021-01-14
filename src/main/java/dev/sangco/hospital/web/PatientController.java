package dev.sangco.hospital.web;

import dev.sangco.hospital.common.ErrorResponse;
import dev.sangco.hospital.common.Result;
import dev.sangco.hospital.domain.Patient;
import dev.sangco.hospital.service.PatientService;
import dev.sangco.hospital.web.dto.PatientCreateRequestDto;
import dev.sangco.hospital.web.dto.PatientResponseDto;
import dev.sangco.hospital.web.dto.PatientUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid PatientCreateRequestDto requestDto, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new Result<ErrorResponse>(ErrorResponse.createErrorResponse(bindingResult)));
        }
        PatientResponseDto responseDto = patientService.save(requestDto);
        return ResponseEntity.created(URI.create(String.format("/patients/%s", responseDto.getId()))).body(new Result<PatientResponseDto>(responseDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid PatientUpdateRequestDto requestDto) {
        patientService.update(id, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        // TODO 데이터 히스토리 데이블로 옮기고 지우기
        patientService.delete(id);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> findById(@PathVariable Long id) {
//
//    }
//
//    @GetMapping
//    public ResponseEntity<?> findAll() {
//
//    }

}
