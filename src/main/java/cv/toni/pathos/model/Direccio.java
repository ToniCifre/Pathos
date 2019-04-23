package cv.toni.pathos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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

    @Column(name = "carrer", unique = true)
    @NotEmpty(message = "La direccio es necessaria")
    private String carrer;

    @Column(name = "num")
    private int num;

    @Column(name = "pis", unique = true)
    @Length(max = 8, message = "Max Length is 8")
    private String pis;

    @Column(name = "porta", unique = true)
    @Length(max = 8, message = "Max Length is 8")
    private String porta;

    @Column(name = "cp")
    @Length(min = 4, message = "Min Length is 4")
    private int cp;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "direccio_user", joinColumns = @JoinColumn(name = "direccio_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

}
