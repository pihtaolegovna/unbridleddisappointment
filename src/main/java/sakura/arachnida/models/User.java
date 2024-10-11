package sakura.arachnida.models;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "\"users\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid", nullable = false)
    private Integer id;

    @NotBlank(message = "Login is required")
    @Column(name = "login", nullable = false)
    private String login;

    @NotBlank(message = "Password is required")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull(message = "Role is required")
    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "isdeleted", nullable = false)  // Изменено на "isdeleted"
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}