package cv.toni.pathos.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "compte")
public class Compte {
    @Id
    @Column(name = "n_compte", length = 15)
    private String nCompte;

    @Column(name = "role")
    private float saldo;
}
