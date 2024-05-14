package vn.unigap.api.dto.in;

import java.util.*;

public class EmployerCreateRequestDTO {
    private String email;
    private String name;
    private int provinceId;
    private String description;
    private static final int MAX_EMAIL_LENGTH = 255;
    private static final int MAX_NAME_LENGTH = 255;

    public EmployerCreateRequestDTO(String email, String name, int provinceId, String description) {
        this.email = email;
        this.name = name;
        this.provinceId = provinceId;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        if (email == null || email.isEmpty()) {
            errors.add("Email is required");
        } else if (!isValidEmail(email)) {
            errors.add("Invalid email format");
        }
        if (name == null || name.isEmpty()) {
            errors.add("Name is required");
        }  else if (!isValidName(name)) {
            errors.add("Invalid name format");
        }
        return errors;
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty() || email.length() > MAX_EMAIL_LENGTH) {
            return false;
        }

        // Regular expression for email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        return email.matches(emailRegex);
    }

    private boolean isValidName(String name) {
        return name != null && !name.isEmpty() && name.length() <= MAX_NAME_LENGTH;
    }
}