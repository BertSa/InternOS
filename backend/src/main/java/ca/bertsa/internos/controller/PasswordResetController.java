package ca.bertsa.internos.controller;

import ca.bertsa.internos.dto.PasswordResetTokenDto;
import ca.bertsa.internos.dto.ResponseMessage;
import ca.bertsa.internos.enums.TypeUser;
import ca.bertsa.internos.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/forgot_password")
public class PasswordResetController {
    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/{type}/{email}")
    public ResponseEntity<?> forgotPasswordMonitor(@PathVariable TypeUser type, @PathVariable String email) {
        try {
            switch (type) {
                case MONITOR:
                    passwordResetService.forgotPasswordMonitor(email);
                    break;
                case SUPERVISOR:
                    passwordResetService.forgotPasswordSupervisor(email);
                    break;
                case STUDENT:
                    passwordResetService.forgotPasswordStudent(email);
                    break;
            }
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseMessage("Un courriel vous a été envoyé pour réinitialiser votre mot de passe"));
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetTokenDto passwordResetTokenDto) {
        try {
            passwordResetService.resetPassword(passwordResetTokenDto);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseMessage("Votre mot de passe a été réinitialisé avec succès!"));
    }
}
