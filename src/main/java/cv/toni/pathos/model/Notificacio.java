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

    @Column(name = "nom")
    @NotEmpty(message = "En nom es necesari")
    @NotNull
    private String nom;

    @Column(name = "descripcio")
    @NotEmpty(message = "La direccio es necessaria")
    @NotNull
    private String descripcio;

    @Column(name = "product")
    @NotNull
    private Boolean product;

    @Column(name = "data")
    @NotNull
    private LocalDateTime data;

    @Column(name = "caducitat")
    @NotNull
    private int caducitat;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "emisor_id")
    @NotNull
    private User emisor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "receptor_id")
    @NotNull
    private User receptor;

    private int receptorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Boolean getProduct() {
        return product;
    }

    public void setProduct(Boolean product) {
        this.product = product;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public int getCaducitat() {
        return caducitat;
    }

    public void setCaducitat(int caducitat) {
        this.caducitat = caducitat;
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

    public int getReceptorId() {
        return receptorId;
    }

    public void setReceptorId(int receptorId) {
        this.receptorId = receptorId;
    }
}
