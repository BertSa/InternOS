package ca.bertsa.internos.model;

import ca.bertsa.internos.enums.Status;
import ca.bertsa.internos.listener.OfferApplicationListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(OfferApplicationListener.class)
@Getter
@Setter
public class OfferApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    private Offer offer;

    @OneToOne
    private Curriculum curriculum;

    private LocalDateTime interviewDate;

    @OneToOne
    private Session session;

}

