package dev.sangco.hospital.web;

import dev.sangco.hospital.common.ErrorResponse;
import dev.sangco.hospital.web.dto.Result;
import dev.sangco.hospital.service.PatientService;
import dev.sangco.hospital.web.dto.PatientCreateRequestDto;
import dev.sangco.hospital.web.dto.PatientResponseDto;
import dev.sangco.hospital.web.dto.PatientUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid PatientCreateRequestDto requestDto, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorResponse.createErrorResponse(bindingResult));
        }
        PatientResponseDto responseDto = patientService.save(requestDto);
        return ResponseEntity.created(URI.create(String.format("/patients/%s", responseDto.getId()))).body(new Result<PatientResponseDto>(responseDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid PatientUpdateRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorResponse.createErrorResponse(bindingResult));
        }
        patientService.update(id, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<PatientResponseDto>> findById(@PathVariable Long id) {
        PatientResponseDto responseDto = patientService.findById(id);
        return ResponseEntity.ok().body(new Result<PatientResponseDto>(responseDto));
    }

    @GetMapping
    public ResponseEntity<Result<Page<PatientResponseDto>>> findAll(Pageable pageable) {
        // TODO 최근 visit만 딱 가지고 올 수 있도록 queryDSL 써서 수정하자.
        Page<PatientResponseDto> responseDtoList = patientService.findAll(pageable);
        return ResponseEntity.ok().body(new Result<Page<PatientResponseDto>>(responseDtoList));
    }

}
