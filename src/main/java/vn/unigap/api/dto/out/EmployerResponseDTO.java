package vn.unigap.api.dto.out;

import vn.unigap.api.entity.Province;

import java.util.Optional;

public class EmployerResponseDTO {
    private long id;
    private String email;
    private String name;
    private int provinceId;
    private String provinceName;
    private String description;
    Province province;

    // Constructors, getters, and setters...

    public EmployerResponseDTO() {
    }

    public EmployerResponseDTO(long id, String email, String name, int provinceId, String provinceName, String description) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.description = description;
    }

    // Getters and setters for all fields

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {this.provinceName = provinceName; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}