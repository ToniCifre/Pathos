package cv.toni.pathos.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "connexio",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_org", "id_user"}))
public class Connexio {

    @NonNull
    @Column(name = "id_org")
    private int idOrg;

    @NonNull
    @Column(name = "id_user")
    private int idUser;



}
