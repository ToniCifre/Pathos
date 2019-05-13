package cv.toni.pathos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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

    @Column(name = "direcció")
    private int num;

    @Column(name = "pis", unique = true)
    @Length(max = 8, message = "Max Length is 8")
    private String pis;

    @Column(name = "porta", unique = true)
    @Length(max = 8, message = "Max Length is 8")
    private String porta;

    @Column(name = "cp")
    @Length(min = 5, max = 5, message = "Length must be 5")
    private int cp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarrer() {
        return carrer;
    }

    public void setCarrer(String carrer) {
        this.carrer = carrer;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }
}
