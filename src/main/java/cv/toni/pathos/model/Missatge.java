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

    @Column(name = "msg", unique = true)
    @NotNull
    private String msg;

    @Column(name = "data", unique = true)
    @NotNull
    private LocalDateTime data;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "emisor_id")
    @NotNull
    private User emisor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "receptor_id")
    @NotNull
    private User receptor;

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

    public User getEmisor() {
        return emisor;
    }

    public void setEmisor(User emisor) {
        this.emisor = emisor;
    }

    public User getReceptor() {
        return receptor;
    }

    public void setReceptor(User receptor) {
        this.receptor = receptor;
    }
}
