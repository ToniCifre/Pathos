package cv.toni.pathos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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
    @Column(name = "user_id", nullable = false, updatable = false,length = 50)
    private int id;

    @Column(name = "email", nullable = false, unique = true, length = 51)
    @NotEmpty(message = "Perfavor, introdueix un email.")
    private String email;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Perfavor, introdueix una contrasenya.")
    private String password;

    @Column(name = "name", unique = true, nullable = false, length = 35)
    @NotEmpty(message = "Perfavor, introdueix un nom.")
    private String name;

    @Column(name = "descripcio", length = 500)
    private String descripcio;

    @Column(name = "active", nullable =  false)
    private int active;

    @Transient
    private Boolean isActive;

    /*@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))*/
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "org_id")
    private User orgId;

    @Column(name = "photo")
    private String photo;

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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    /*public Set<Role> getRoles() { return roles; }

    public void setRoles(Set<Role> roles) { this.roles = roles; }*/

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public User getOrgId() {
        return orgId;
    }

    public void setOrgId(User orgId) {
        this.orgId = orgId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean isActive) {
        isActive = isActive;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
