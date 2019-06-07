package cv.toni.pathos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notificacio")
public class Notificacio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notifi_id")
    private int id;

    @Column(name = "titol", nullable = false)
    @NotEmpty(message = "En nom es necesari")
    private String titol;

    @Column(name = "descripcio", nullable = false, length = 500)
    @NotEmpty(message = "La direccio es necessaria")
    private String descripcio;

    @Column(name = "isProduct", nullable = false)
    private Boolean isProduct;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Column(name = "caducitat")
    private String caducitat;

    @Enumerated(EnumType.STRING)
    @Column(name = "estat", nullable = false, length = 10)
    private NotifyStat estat;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false, name = "emisor_id")
    private User emisor;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false, name = "receptor_id")
    private User receptor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "direccio_id")
    private Direccio direccio;

    @Transient
    @NotNull(message = "has d'escollir una direcció")
    private int id_direccio;

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getTitol() { return titol; }

    public void setTitol(String titol) { this.titol = titol; }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Boolean isProduct() {
        return isProduct;
    }

    public void setProduct(Boolean isProduct) {
        this.isProduct = isProduct;
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

    public Direccio getDireccio() {
        return direccio;
    }

    public void setDireccio(Direccio direccio) {
        this.direccio = direccio;
    }

    public int getId_direccio() {
        return id_direccio;
    }

    public String getCaducitat() { return caducitat; }

    public void setCaducitat(String caducitat) { this.caducitat = caducitat; }

    public NotifyStat getEstat() { return estat; }

    public void setEstat(NotifyStat estat) { this.estat = estat; }
}

