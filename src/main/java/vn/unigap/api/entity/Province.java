package vn.unigap.api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "JOB_PROVINCE")
public class Province {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer Id;

    private String name;

    public Province() {
        // Default constructor required by Hibernate
    }

    public Province(Integer id, String name) {
        this.Id = id;
        this.name = name;
    }
}
