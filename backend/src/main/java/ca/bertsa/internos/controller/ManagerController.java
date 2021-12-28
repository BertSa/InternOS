package ca.bertsa.internos.controller;

import ca.bertsa.internos.dto.ResponseMessage;
import ca.bertsa.internos.model.Manager;
import ca.bertsa.internos.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
@CrossOrigin
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        try {
            Manager manager = managerService.getOneByEmailAndPassword(email, password);
            return ResponseEntity.ok(manager);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> checkValidEmail(@PathVariable String email) {
        return ResponseEntity.ok(managerService.isEmailInvalid(email));
    }
}
