package vn.unigap.api.dto.in;

import java.util.ArrayList;
import java.util.List;

public class EmployerUpdateRequestDTO {
    private String name;
    private int provinceId;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> validateUpdate(long id) {
        List<String> errors = new ArrayList<>();

        if (id <= 0) {
            errors.add("ID must be greater than 0");
        }
        // Validate provinceId format
        try {
            Integer.parseInt(String.valueOf(provinceId));
        } catch (NumberFormatException e) {
            errors.add("Province ID must be an integer");
        }

        return errors;
    }

}
