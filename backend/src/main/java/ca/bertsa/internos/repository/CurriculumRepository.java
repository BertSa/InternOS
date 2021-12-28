package ca.bertsa.internos.repository;

import ca.bertsa.internos.model.Curriculum;
import ca.bertsa.internos.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
    List<Curriculum> findAllByStudent(Student student);
}
