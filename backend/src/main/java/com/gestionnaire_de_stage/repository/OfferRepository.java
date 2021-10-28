package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByDepartmentIgnoreCaseAndValidIsTrue(String department);
    List<Offer> findAllByValid(Boolean valid);

    List<Offer> findAllByValidIsNull();
}
