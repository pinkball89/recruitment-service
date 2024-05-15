package vn.unigap.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "JOB_PROVINCE")
public class Province {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer Id;
    private String name;

}
