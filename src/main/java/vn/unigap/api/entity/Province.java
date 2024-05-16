package vn.unigap.api.entity;

import jakarta.persistence.*;

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

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
