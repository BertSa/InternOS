package ca.bertsa.internos.controller;

import ca.bertsa.internos.dto.AssignDto;
import ca.bertsa.internos.dto.ResponseMessage;
import ca.bertsa.internos.model.OfferApplication;
import ca.bertsa.internos.model.Student;
import ca.bertsa.internos.model.Supervisor;
import ca.bertsa.internos.service.StudentService;
import ca.bertsa.internos.service.SupervisorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/supervisor")
public class SupervisorController {

    private final SupervisorService supervisorService;
    private final StudentService studentService;

    public SupervisorController(SupervisorService supervisorService, StudentService studentService) {
        this.supervisorService = supervisorService;
        this.studentService = studentService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Supervisor supervisor) {
        Supervisor createdSupervisor;
        try {
            createdSupervisor = supervisorService.create(supervisor);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSupervisor);
    }

    @GetMapping("/matricule/{matricule}")
    public ResponseEntity<?> checkValidMatricule(@PathVariable String matricule) {
        return ResponseEntity.ok(!supervisorService.isMatriculeValid(matricule));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> checkValidEmail(@PathVariable String email) {
        return ResponseEntity.ok(supervisorService.isEmailInvalid(email));
    }

    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        Supervisor supervisor;
        try {
            supervisor = supervisorService.getOneByEmailAndPassword(email, password);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(supervisor);
    }

    @GetMapping
    public ResponseEntity<?> getAllSupervisor() {
        return ResponseEntity.ok(supervisorService.getAll());
    }

    @PostMapping("/assign/student")
    public ResponseEntity<?> AssignSupervisor(@RequestBody AssignDto assignDto) {
        Student student;
        Supervisor supervisor;
        try {
            student = studentService.getOneByID(assignDto.getIdStudent());
            supervisor = supervisorService.getOneByID(assignDto.getIdSupervisor());
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        boolean assign = studentService.assign(student, supervisor);
        if (assign) {
            return ResponseEntity.ok(new ResponseMessage("Affectation faite!"));
        }
        return ResponseEntity
                .badRequest()
                .body(new ResponseMessage("Affectation rejetée, l'étudiant est déjà assigné!"));
    }

    @GetMapping("/students_status/{supervisor_id}")
    public ResponseEntity<?> getAllStudentsStatus(@PathVariable Long supervisor_id) {
        List<OfferApplication> offerApplicationList;
        try {
            offerApplicationList = supervisorService.getStudentsStatus(supervisor_id);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
        return ResponseEntity
                .ok(offerApplicationList);
    }

    @PutMapping("/change_password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody String password) {
        try {
            supervisorService.changePassword(id, password);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseMessage("Mot de passe changé avec succès"));
    }
}
