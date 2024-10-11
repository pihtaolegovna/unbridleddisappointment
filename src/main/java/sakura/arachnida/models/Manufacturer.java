package sakura.arachnida.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "manufacturer")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('manufacturer_manufacturerid_seq')")
    @Column(name = "manufacturerid", nullable = false)
    private Integer id;

    @NotBlank(message = "Manufacturer name cannot be blank")
    @Size(max = 100, message = "Manufacturer name must not exceed 100 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "isdeleted", nullable = false)
    @ColumnDefault("false")
    private boolean isDeleted;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}