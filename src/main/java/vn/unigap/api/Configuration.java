package vn.unigap.api;

import org.springframework.context.annotation.Bean;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.repository.EmployerRepository;

import java.util.ArrayList;
import java.util.List;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public EmployerRepository employerRepository(){
        return new EmployerRepository() {
            List<Employer> employerList = new ArrayList<>();
            @Override
            public void save(Employer employer) {
                employerList.add(employer);
            }

            @Override
            public boolean existsById(long id) {
                return false;
            }

            @Override
            public boolean existsByEmail(String email) {
                return false;
            }

            @Override
            public boolean existsByProvinceID(int province) {
//                return false;
                //For testing API purpose only without database
                return true;
            }

            @Override
            public Employer findById(long id) {
                return null;
            }

            @Override
            public List<Employer> findAll(int page, int pageSize) {
                return null;
            }

            @Override
            public void deleteById(long id) {
            }
        };
    }
}
