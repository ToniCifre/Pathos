package cv.toni.pathos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "direccio")
public class Direccio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "direccio_id")
    private int id;

    @Column(name = "dir", nullable = false)
    @NotEmpty(message = "La direccio es necessaria")
    private String dir;

    @Column(name = "cp", nullable = false)
    @NotNull(message = "La codi postal es necessari")
    private int cp;

    @ManyToOne
    @JoinColumn(name = "u_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "direccio")
    private List<Notificacio> notificacios;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccio() {
        return dir;
    }

    public void setDireccio(String direccio) {
        this.dir = direccio;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Direccio getThis(){return this;}

    public List<Notificacio> getNotificacios() {
        return notificacios;
    }

    public void setNotificacios(List<Notificacio> notificacios) {
        this.notificacios = notificacios;
    }
}
