package cv.toni.pathos.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@Builder
@NoArgsConstructor
@Entity
@Table(name = "sala")
public class Sala {

    @EmbeddedId
    private SalaId salaId;

    public Sala(SalaId salaId) {
        this.salaId = salaId;
    }

    public SalaId getSalaId() {
        return salaId;
    }

    public void setSalaId(SalaId salaId) {
        this.salaId = salaId;
    }
}
