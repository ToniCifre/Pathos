package cv.toni.pathos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user",
        uniqueConstraints=@UniqueConstraint(columnNames={"user_id", "email"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, updatable = false, length = 50)
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

    @Column(name = "active", nullable = false)
    private int active;

    @Transient
    public Boolean isActive;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Compte> comptes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "org")
    private List<User> colaboradors;

    @ManyToOne
    private User org;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Direccio> direccions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "org")
    private List<Sala> salas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "per")
    private List<Sala> salasP;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receptor")
    private List<Notificacio> notificacionsRebudes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emisor")
    private List<Notificacio> notificacionsEnviades;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recollidor")
    private List<Notificacio> notificacionsAcceptades;

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

    public List<User> getColaboradors() {
        return colaboradors;
    }

    public void setColaboradors(List<User> colaboradors) {
        this.colaboradors = colaboradors;
    }

    public void addColaborador(User colaborador) {
        this.colaboradors.add(colaborador);
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Direccio> getDireccions() {
        return direccions;
    }

    public void setDireccions(List<Direccio> direccions) {
        this.direccions = direccions;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }

    public List<Notificacio> getNotificacionsRebudes() {
        return notificacionsRebudes;
    }

    public void setNotificacionsRebudes(List<Notificacio> notificacionsRebudes) {
        this.notificacionsRebudes = notificacionsRebudes;
    }

    public List<Notificacio> getNotificacionsEnviades() {
        return notificacionsEnviades;
    }

    public void setNotificacionsEnviades(List<Notificacio> notificacionsEnviades) {
        this.notificacionsEnviades = notificacionsEnviades;
    }

    public User getOrg() {
        return org;
    }

    public void setOrg(User org) {
        this.org = org;
    }
}
