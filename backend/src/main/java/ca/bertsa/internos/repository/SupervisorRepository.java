package ca.bertsa.internos.repository;

import ca.bertsa.internos.model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

    Supervisor findSupervisorByEmailAndPassword(String email, String password);

    Supervisor getByEmail(String email);

    boolean existsByMatricule(String matricule);
}
