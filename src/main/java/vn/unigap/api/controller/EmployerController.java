package vn.unigap.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.EmployerCreateRequestDTO;
import vn.unigap.api.dto.in.EmployerUpdateRequestDTO;
import vn.unigap.api.dto.out.ApiResponseDTO;
import vn.unigap.api.dto.out.EmployerResponseDTO;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.service.EmployerService;

import java.util.List;

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
        try {
            Employer employer = employerService.createEmployer(requestCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponseDTO(0, 201, "Employer created successfully", employer));

        } catch (Exception e) {
            return toApiResponse(e);
        }
    }

    private static ResponseEntity<ApiResponseDTO> toApiResponse(Exception e) {
        if (e instanceof EmployerService.BadInputException) {
            //Catching our own BadInputException
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO(1, 400, e.getMessage(), null));
        } else {
            //Catching any exception that is not created intentionally
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ApiResponseDTO(1, 500, e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> updateEmployer(@PathVariable long id,
                                                         @RequestBody EmployerUpdateRequestDTO requestUpdateDTO) {
        //Update employer
        try {
            Employer employer = employerService.updateEmployer(id, requestUpdateDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO(0, 200, "Employer updated " +
                    "successfully", employer));
        } catch (Exception e) {
            return toApiResponse(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> getEmployer(@PathVariable long id) {
        try {
            Employer employer = employerService.getEmployer(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO(0, 200, "Employer " +
                    "retrieved" +
                    " successfully", employer));
        } catch (Exception e) {
            return toApiResponse(e);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO> listEmployers(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<EmployerResponseDTO> employerResponseDTO =
                    employerService.listEmployers(page, pageSize).stream().map(this::mapToResponseDTO).toList();

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO(0, 200, "Employers " +
                    "retrieved successfully", employerResponseDTO));

        } catch (Exception e) {
            return toApiResponse(e);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> deleteEmployer(@PathVariable long id) {
        try {
            Employer employer = employerService.deleteEmployer(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO(0, 200, "Employer " +
                    "deleted successfully", employer));
        } catch (Exception e) {
            return toApiResponse(e);
        }

    }

    private EmployerResponseDTO mapToResponseDTO(Employer employer) {
        EmployerResponseDTO responseDTO = new EmployerResponseDTO();
        responseDTO.setId(employer.getId());
        responseDTO.setEmail(employer.getEmail());
        responseDTO.setName(employer.getName());
        responseDTO.setProvinceId(employer.getProvinceId());
        responseDTO.setProvinceName(employer.getProvinceName());
        responseDTO.setDescription(employer.getDescription());
        return responseDTO;
    }
}
