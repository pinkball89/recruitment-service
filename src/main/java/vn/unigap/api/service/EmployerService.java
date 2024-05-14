package vn.unigap.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.EmployerCreateRequestDTO;
import vn.unigap.api.dto.in.EmployerUpdateRequestDTO;
import vn.unigap.api.dto.out.ApiResponseDTO;
import vn.unigap.api.dto.out.EmployerResponseDTO;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.repository.EmployerRepository;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class EmployerService {

    public static class BadInputException extends RuntimeException {
        public BadInputException(String message) {
            super(message);
        }
    }

    private final EmployerRepository employerRepository;

    @Autowired
    public EmployerService(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    public Employer createEmployer(EmployerCreateRequestDTO request) {
        // Validate input data
        List<String> validationErrors = request.validate();
        if (!validationErrors.isEmpty()) {
            throw new BadInputException(validationErrors.toString());
        }

        // Check email uniqueness
        if (employerRepository.existsByEmail(request.getEmail())) {
            throw new BadInputException("Email " + request.getEmail() + " already exists");
        }
        // Check existence of provinceId
        if (!employerRepository.existsByProvinceID(request.getProvinceId())) {
            throw new BadInputException("Province ID " + request.getProvinceId() + " does not exist");
        }
        // Generate timestamps for created_at and updated_at
        Date now = new Date();

        // Create Employer object
        Employer employer = new Employer();
        employer.setEmail(request.getEmail());
        employer.setName(request.getName());
        employer.setProvince(request.getProvinceId());
        employer.setDescription(request.getDescription());
        employer.setCreated_at(now);
        employer.setUpdated_at(now);

        // Call repository layer to create employer
        employerRepository.save(employer);

        return employer;
    }

//    public ApiResponseDTO createEmployer(EmployerCreateRequestDTO request) {
//        // Validate input data
//        List<String> validationErrors = request.validate();
//        if (!validationErrors.isEmpty()) {
//            return new ApiResponseDTO(400, "Bad Request", validationErrors.toString(), null);
//        }
//
//        // Check email uniqueness
//        if (employerRepository.existsByEmail(request.getEmail())) {
//            return new ApiResponseDTO(400, "Bad Request", "Email already exists", null);
//        }
//        // Check existence of provinceId
//        if (!employerRepository.existsByProvinceID(request.getProvinceId())) {
//            return new ApiResponseDTO(400, "Bad Request", "Province ID does not exist", null);
//        }
//        // Generate timestamps for created_at and updated_at
//        Date now = new Date();
//
//        // Create Employer object
//        Employer employer = new Employer();
//        employer.setEmail(request.getEmail());
//        employer.setName(request.getName());
//        employer.setProvince(request.getProvinceId());
//        employer.setDescription(request.getDescription());
//        employer.setCreated_at(now);
//        employer.setUpdated_at(now);
//
//        // Call repository layer to create employer
//        employerRepository.save(employer);
//
//        return new ApiResponseDTO(201, "Created", "Employer created successfully", null);
//    }

    public ApiResponseDTO updateEmployer(long id, EmployerUpdateRequestDTO request) {
        //validate input data
        List<String> validationErrors = request.validateUpdate(id);
        if (!validationErrors.isEmpty()) {
            return new ApiResponseDTO(400, "Bad Request", validationErrors.toString(), null);
        }
        //Check if employer id exists
        if (!employerRepository.existsById(id)) {
            return new ApiResponseDTO(404, "Not Found", "Employer not found", null);
        }
        //Update employer information
        Employer employer = employerRepository.findById(id);
        employer.setName(request.getName());
        employer.setProvince(request.getProvinceId());
        employer.setDescription(request.getDescription());
        employer.setUpdated_at(new Date());

        // Call repository layer to update employer
        employerRepository.save(employer);

        return new ApiResponseDTO(200, "OK", "Employer updated successfully", null);
    }

    public ApiResponseDTO getEmployer(long id) {
        if (!employerRepository.existsById(id)) {
            return new ApiResponseDTO(404, "Not Found", "Employer not found", null);
        }

        Employer employer = employerRepository.findById(id);
        EmployerResponseDTO responseDTO = mapToResponseDTO(employer);

        return new ApiResponseDTO(200, "OK", "Employer retrieved successfully", responseDTO);
    }

    public ApiResponseDTO listEmployers(int page, int pageSize) {
        List<Employer> employers = employerRepository.findAll(page, pageSize);
        List<EmployerResponseDTO> employerDTOs = employers.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());

        EmployerListResponseDTO responseDTO = new EmployerListResponseDTO();
        responseDTO.setData(employerDTOs);

        return new ApiResponseDTO(200, "OK", "Employers retrieved successfully", responseDTO);
    }

    public ApiResponseDTO deleteEmployer(long id) {
        if (!employerRepository.existsById(id)) {
            return new ApiResponseDTO(404, "Not Found", "Employer not found", null);
        }

        employerRepository.deleteById(id);

        return new ApiResponseDTO(200, "OK", "Employer deleted successfully", null);
    }

    private EmployerResponseDTO mapToResponseDTO(Employer employer) {
        EmployerResponseDTO responseDTO = new EmployerResponseDTO();
        responseDTO.setId(employer.getId());
        responseDTO.setEmail(employer.getEmail());
        responseDTO.setName(employer.getName());
        responseDTO.setProvinceId(employer.getProvince());
        responseDTO.setProvinceName(getProvinceName(employer.getProvince()));
        responseDTO.setDescription(employer.getDescription());
        return responseDTO;
    }

}
