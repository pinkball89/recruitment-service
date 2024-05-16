package vn.unigap.api.entity;

import java.util.*;

import jakarta.persistence.*;

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

    public void setId(Long id) {
        this.id = id;
    }

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
        return province.getId();
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getProvinceName() {
        return province.getName();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Employer{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", province=" + province +
                ", description='" + description + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
