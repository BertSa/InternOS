package ca.bertsa.internos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ca.bertsa.internos.listener.OfferListener;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Entity
@EntityListeners(OfferListener.class)
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Monitor creator;

    @NotBlank(message = "Le département ne peut pas être vide.")
    @Column(nullable = false)
    private String department;

    @NotBlank(message = "Le titre ne peut pas être vide.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "La description ne peut pas être vide.")
    @Column(nullable = false)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date created = new Date();

    @NotBlank(message = "L'adresse ne peut pas être vide.")
    @Column(nullable = false)
    private String address;

    @Min(value = 0, message = "Le salaire ne peut être négatif.")
    private double salary;

    private Boolean valid;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private String nbSemaine;

    private String horaireTravail;

    private String nbHeureSemaine;

    @ManyToOne
    private Session session;

    @Transient
    public String creatorEmail() {
        if (creator == null)
            return null;
        return creator.getEmail();
    }
}
