package ca.bertsa.internos.repository;

import ca.bertsa.internos.model.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitorRepository extends JpaRepository<Monitor, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

    Monitor findMonitorByEmailAndPassword(String email, String password);

    Monitor getMonitorByEmail(String email);
}
