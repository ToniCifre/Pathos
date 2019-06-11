package cv.toni.pathos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "missatge")
public class Missatge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "msg_id")
    private int id;

    @Column(name = "msg")
    @NotNull(message = "No es pot enviar un misatge buit")
    private String msg;

    @Column(name = "data")
    @NotNull
    private LocalDateTime data;

    @Column(name = "llegit")
    @NotNull
    private boolean llegit;

    @Column(name = "is_org")
    @NotNull
    private boolean senderOrg;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumns({
            @JoinColumn(
                    name = "pers_id",
                    referencedColumnName = "pers_id"),
            @JoinColumn(
                    name = "org_id",
                    referencedColumnName = "org_id")
    })
    private Sala sala;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public boolean isLlegit() {
        return llegit;
    }

    public void setLlegit(boolean llegit) {
        this.llegit = llegit;
    }

    public boolean idSenderOrg() {
        return senderOrg;
    }

    public void setOrg(boolean org) {
        senderOrg = org;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
}
