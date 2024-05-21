package vn.unigap.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.EmployerCreateRequestDTO;
import vn.unigap.api.dto.in.EmployerUpdateRequestDTO;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.entity.Province;
import vn.unigap.api.exception.ApiException;
import vn.unigap.api.repository.EmployerRepository;
import vn.unigap.api.repository.ProvinceRepository;

import java.util.*;



@Service
public class EmployerService {
    private final ProvinceRepository provinceRepository;
    private final EmployerRepository employerRepository;


    @Autowired
    public EmployerService(EmployerRepository employerRepository, ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
        this.employerRepository = employerRepository;
    }

    public Employer createEmployer(EmployerCreateRequestDTO request) {
        // Validate input data
        List<String> validationErrors = request.validate();
        if (!validationErrors.isEmpty()) {
            throw new ApiException.BadInputException(validationErrors.toString());
        }
        // Check email uniqueness
        if (employerRepository.existsByEmail(request.getEmail())) {
            throw new ApiException.BadInputException("Email " + request.getEmail() + " already exists");
        }
        // Check existence of provinceId
        if (!provinceRepository.existsById(request.getProvinceId())) {
            throw new ApiException.BadInputException("Province ID " + request.getProvinceId() + " does not exist");
        }
        // Generate timestamps for created_at and updated_at
        Date now = new Date();

        // Create Employer object
        Employer employer = new Employer();
        employer.setEmail(request.getEmail());
        employer.setName(request.getName());
        //Fetch the Province object based on the provided provinceId
        Province province = provinceRepository.findById(request.getProvinceId()).orElse(null);
        employer.setProvince(province);
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
            throw new ApiException.BadInputException(validationErrors.toString());
        }
        //Check if employer id exists
        if (!employerRepository.existsById(id)) {
            throw new ApiException.NotFoundException("Employer ID " + id + " Not Found");
        }
        // Check existence of provinceId
        if (!provinceRepository.existsById(request.getProvinceId())) {
            throw new ApiException.BadInputException("Province ID " + request.getProvinceId() + " does not exist");
        }
        //Update employer information
        Employer employer = employerRepository.findById(id);
        employer.setName(request.getName());
        //Fetch the Province object based on the provided provinceId
        Province province = provinceRepository.findById(request.getProvinceId()).orElse(null);
        employer.setProvince(province);
        employer.setDescription(request.getDescription());
        employer.setUpdated_at(new Date());

        // Call repository layer to update employer
        employerRepository.save(employer);

        return employer;
    }

    public Employer getEmployer(long id) {
        if (!employerRepository.existsById(id)) {
            throw new ApiException.NotFoundException("Employer ID " + id + " Not Found");
        }

        return employerRepository.findById(id);
    }

    public List<Employer> listEmployers(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page -1 , pageSize, Sort.by("name"));
        Page<Employer> employers = employerRepository.findAll(pageable);
        return employers.stream().toList();
    }

    public Employer deleteEmployer(long id) {
        if (!employerRepository.existsById(id)) {
            throw new ApiException.NotFoundException("Employer ID " + id + " Not Found");
        }
        Employer employer = employerRepository.findById(id);
        employerRepository.deleteById(id);

        return employer;
    }
}