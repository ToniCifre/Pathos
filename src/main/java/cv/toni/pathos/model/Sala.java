package cv.toni.pathos.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name = "sala")
public class Sala {

    @EmbeddedId
    private SalaId salaId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User org;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User per;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sala")
    private List<Missatge> missatges;

    public Sala(SalaId salaId, User u, User p) {
        this.salaId = salaId;
        org = u;
        per = p;
    }
    public Sala(SalaId salaId) {
        this.salaId = salaId;
    }

    public SalaId getSalaId() {
        return salaId;
    }

    public void setSalaId(SalaId salaId) {
        this.salaId = salaId;
    }

    public User getOrg() {
        return org;
    }

    public void setOrg(User org) {
        this.org = org;
    }

    public List<Missatge> getMissatges() {
        return missatges;
    }

    public void setMissatges(List<Missatge> missatges) {
        this.missatges = missatges;
    }
}
