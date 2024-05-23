package vn.unigap.api.entity;

import java.util.*;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "EMPLOYER")
public class Employer {
    //Have to modify the table imported from job_db.sql to make the id column auto increment
    //for the identity strategy to work
    //alter table employer modify column id bigint auto_increment;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Lob
    @Column(name= "Email", unique = true)
    private String email;

    @Lob
    @Column(name= "Name", length = 256)
    private String name;

    @ManyToOne
    @JoinColumn(name= "Province")
    private Province province;

    //@ManyToOne: many employer instances can be associated with one Province instance
    //@JoinColumn: join column relationship, indicates that the column name "province" in the employer table will be
    // used to join with the "id" column in the referenced table (Province)


    @Lob
    @Column(name= "Description", length = 256)
    private String description;
    private Date created_at;
    private Date updated_at;

    public int getProvinceId() {
        return province.getId();
    }

    public String getProvinceName() {
        return province.getName();
    }

}
