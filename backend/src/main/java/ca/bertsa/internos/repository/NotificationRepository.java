package ca.bertsa.internos.repository;

import ca.bertsa.internos.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllBySeenIsFalseAndTargetedUser_IdOrderByCreatedDateDesc(Long userId);
}
