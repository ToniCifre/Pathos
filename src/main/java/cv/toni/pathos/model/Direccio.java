package cv.toni.pathos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "u_id", nullable = false)
    private User user;

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
}
