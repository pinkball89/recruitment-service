package vn.unigap.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.EmployerCreateRequestDTO;
import vn.unigap.api.dto.in.EmployerUpdateRequestDTO;
import vn.unigap.api.dto.out.EmployerResponseDTO;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.repository.EmployerRepository;
import vn.unigap.api.repository.ProvinceRepository;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class EmployerService {
    private final ProvinceRepository provinceRepository;
    private final EmployerRepository employerRepository;

    public static class BadInputException extends RuntimeException {
        public BadInputException(String message) {
            super(message);
        }
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) {
            super(message);
        }
    }


    @Autowired
    public EmployerService(EmployerRepository employerRepository, ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
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
        if (!provinceRepository.existsById(request.getProvinceId())) {
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

    public Employer updateEmployer(long id, EmployerUpdateRequestDTO request) {
        //validate input data
        List<String> validationErrors = request.validateUpdate(id);
        if (!validationErrors.isEmpty()) {
            throw new BadInputException(validationErrors.toString());
        }
        //Check if employer id exists
        if (!employerRepository.existsById(id)) {
            throw new NotFoundException("Employer ID " + id + " Not Found");
        }
        //Update employer information
        Employer employer = employerRepository.findById(id);
        employer.setName(request.getName());
        employer.setProvince(request.getProvinceId());
        employer.setDescription(request.getDescription());
        employer.setUpdated_at(new Date());

        // Call repository layer to update employer
        employerRepository.save(employer);

        return employer;
    }

    public Employer getEmployer(long id) {
        if (!employerRepository.existsById(id)) {
            throw new NotFoundException("Employer ID " + id + " Not Found");
        }

        Employer employer = employerRepository.findById(id);
        EmployerResponseDTO responseDTO = mapToResponseDTO(employer);

        return employer;
    }

    public List<EmployerResponseDTO> listEmployers(int page, int pageSize) {
        List<Employer> employers = employerRepository.findAll();
        List<EmployerResponseDTO> employerDTOs = employers.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());

        return employerDTOs;
    }

    public Employer deleteEmployer(long id) {
        if (!employerRepository.existsById(id)) {
            throw new NotFoundException("Employer ID " + id + " Not Found");
        }
        Employer employer = employerRepository.findById(id);
        employerRepository.deleteById(id);

        return employer;
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

    private String getProvinceName(int province) {
        return "Not know yet";
    }

}
