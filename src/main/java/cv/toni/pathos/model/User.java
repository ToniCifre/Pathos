package cv.toni.pathos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user",
        uniqueConstraints=@UniqueConstraint(columnNames={"user_id", "email"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    @NotNull
    private int id;

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Please provide a valid Email")
    @NotEmpty(message = "Please provide an email")
    @Length(max = 50, message = "El email es demasiado largo")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "Your password must have at least 5 characters")
    @NotEmpty(message = "Please provide your password")
    @NotNull
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "Please provide your name")
    @NotNull
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "active")
    @NotNull
    private int active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @NotNull
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(name = "u_direct", joinColumns = @JoinColumn(name = "u_id"), inverseJoinColumns = @JoinColumn(name = "d_id"))
    private Set<Direccio> direccions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Set<Role> getRoles() { return roles; }

    public void setRoles(Set<Role> roles) { this.roles = roles; }

    public Set<Direccio> getDireccions() {
        return direccions;
    }

    public void setDireccions(Set<Direccio> direccions) {
        this.direccions = direccions;
    }
}
