package ca.bertsa.internos.controller;

import ca.bertsa.internos.dto.ResponseMessage;
import ca.bertsa.internos.dto.StudentCurriculumsDTO;
import ca.bertsa.internos.dto.ValidationCurriculum;
import ca.bertsa.internos.model.Curriculum;
import ca.bertsa.internos.model.Student;
import ca.bertsa.internos.service.CurriculumService;
import ca.bertsa.internos.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/curriculum")
public class CurriculumController {

    private final CurriculumService curriculumService;

    private final StudentService studentService;

    public CurriculumController(CurriculumService curriculumService, StudentService studentService) {
        this.curriculumService = curriculumService;
        this.studentService = studentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadCurriculum(@RequestParam("file") MultipartFile file,
                                                            @RequestParam("id") Long studentId) {
        Curriculum curriculum;
        try {
            curriculum = curriculumService.convertMultipartFileToCurriculum(file, studentId);
            curriculumService.create(curriculum);
        } catch (IOException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur de téléversement. Réessayer plus tard"));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseMessage("Le curriculum a été téléversé avec succès"));
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> getAllCurriculumByStudentId(@PathVariable Long id) {
        List<Curriculum> curriculumList;
        try {
            curriculumList = curriculumService.findAllByStudentId(id);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(curriculumList);
    }

    @GetMapping("/all_student/{studentID}")
    public ResponseEntity<?> allCurriculumsByStudentAsStudentCurriculumsDTO(@PathVariable long studentID) {
        try {
            Student student = studentService.getOneByID(studentID);
            StudentCurriculumsDTO studentCurriculumsDTO = curriculumService.allCurriculumsByStudentAsStudentCurriculumsDTO(student);
            return ResponseEntity.ok(studentCurriculumsDTO);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody ValidationCurriculum validationCurriculum) {
        try {
            curriculumService.validate(validationCurriculum);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        String response = validationCurriculum.isValid() ? "Curriculum validé!" : "Curriculum rejeté!";
        return ResponseEntity.ok(new ResponseMessage(response));
    }

    @DeleteMapping("/delete/{curriculumId}")
    public ResponseEntity<?> deleteOneById(@PathVariable long curriculumId) {
        try {
            curriculumService.deleteOneById(curriculumId);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseMessage("Curriculum effacé avec succès"));
    }
}
