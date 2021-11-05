package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferApplicationRepository extends JpaRepository<OfferApplication, Long> {
    boolean existsByOfferAndCurriculum(Offer offer, Curriculum curriculum);

    List<OfferApplication> getAllByOffer_CreatorEmail(String email);

    List<OfferApplication> getAllByStatus(Status status);
    List<OfferApplication> getAllByStatusAndCurriculum_StudentId(Status status, Long id);
}
