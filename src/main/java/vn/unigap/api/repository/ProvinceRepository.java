package vn.unigap.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.unigap.api.entity.Province;

public interface ProvinceRepository extends JpaRepository<Province, Integer> {
}
