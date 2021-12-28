package ca.bertsa.internos.repository;

import ca.bertsa.internos.enums.TypeSession;
import ca.bertsa.internos.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    boolean existsByTypeSessionAndYear(TypeSession typeSession, Year year);

    List<Session> findAllByYearGreaterThanEqual(Year year);
}