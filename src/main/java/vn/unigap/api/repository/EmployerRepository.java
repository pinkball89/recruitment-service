package vn.unigap.api.repository;


import vn.unigap.api.entity.Employer;

import java.util.List;

public interface EmployerRepository {
    void save(Employer employer);
    boolean existsById(long id);
    boolean existsByEmail(String email);
    boolean existsByProvinceID(int province);

    Employer findById(long id);

    List<Employer> findAll(int page, int pageSize);
}
