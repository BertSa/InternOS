package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.exception.SupervisorAlreadyExistsException;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.SupervisorRepository;
import com.gestionnaire_de_stage.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/supervisor")
public class SupervisorController {

    @Autowired
    SupervisorService supervisorService;

    @Autowired
    SupervisorRepository supervisorRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody Supervisor supervisor) throws SupervisorAlreadyExistsException {
        if (supervisor.getEmail() != null && supervisorRepository.existsByEmail(supervisor.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Ce courriel existe déja!"));
        }
        Supervisor supervisor1 = supervisorService.create(supervisor);

        return new ResponseEntity<>(supervisor1, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidRequests(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        Optional<Supervisor> supervisor = supervisorService.getOneByEmailAndPassword(email, password);
        if (supervisor.isPresent()) {
            return ResponseEntity.ok(supervisor.get());
        }
        return ResponseEntity.badRequest().body(new ResponseMessage("Erreur: Courriel ou Mot de Passe Invalide"));
    }
}
