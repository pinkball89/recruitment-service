package vn.unigap.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.EmployerCreateRequestDTO;
import vn.unigap.api.dto.in.EmployerUpdateRequestDTO;
import vn.unigap.api.dto.out.ApiResponseDTO;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.service.EmployerService;

@RestController
@RequestMapping("/employers")
public class EmployerController {
    private final EmployerService employerService;

    @Autowired
    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponseDTO> createEmployer(@RequestBody EmployerCreateRequestDTO requestCreateDTO) {
        // Create the employer
        Employer response = employerService.createEmployer(requestCreateDTO);

        // Check if the employer was successfully created
        if (response.getStatusCode() == HttpStatus.CREATED.value()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> updateEmployer(@PathVariable long id,
                                                         @RequestBody EmployerUpdateRequestDTO requestUpdateDTO) {
        //Update employer
        ApiResponseDTO response = employerService.updateEmployer(id, requestUpdateDTO);

        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> getEmployer(@PathVariable long id) {
        ApiResponseDTO response = employerService.getEmployer(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO> listEmployers(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        ApiResponseDTO response = employerService.listEmployers(page, pageSize);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> deleteEmployer(@PathVariable long id) {
        ApiResponseDTO response = employerService.deleteEmployer(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
