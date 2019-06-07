package cv.toni.pathos.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SalaId implements Serializable {
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "pers_id")
    @NotNull
    private User personaId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "org_id")
    @NotNull
    private User orgId;


    public User getPersonaId() {
        return personaId;
    }

    public void setPersonaId(User personaId) {
        this.personaId = personaId;
    }

    public User getOrgId() {
        return orgId;
    }

    public void setOrgId(User orgId) {
        this.orgId = orgId;
    }
}
