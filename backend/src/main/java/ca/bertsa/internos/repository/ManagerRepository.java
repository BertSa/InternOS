package ca.bertsa.internos.repository;

import ca.bertsa.internos.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Manager findManagerByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);
}
