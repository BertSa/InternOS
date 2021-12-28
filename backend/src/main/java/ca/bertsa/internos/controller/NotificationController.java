package ca.bertsa.internos.controller;

import ca.bertsa.internos.dto.ResponseMessage;
import ca.bertsa.internos.model.Notification;
import ca.bertsa.internos.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getAllByUser(@PathVariable long userId) {
        try {
            List<Notification> notifications = notificationService.getAllByUserId(userId);
            return ResponseEntity
                    .ok(notifications);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping("/set_seen/{notificationId}")
    public ResponseEntity<?> updateSeen(@PathVariable long notificationId) {
        try {
            Notification notification = notificationService.updateSeen(notificationId);
            return ResponseEntity
                    .ok(notification);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
    }
}
