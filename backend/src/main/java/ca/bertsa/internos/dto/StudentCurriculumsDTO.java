package ca.bertsa.internos.dto;

import ca.bertsa.internos.model.Curriculum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StudentCurriculumsDTO {

    private Curriculum principal;

    private List<Curriculum> curriculumList;

    public StudentCurriculumsDTO(Curriculum principal, List<Curriculum> curriculumDTOS) {
        this.principal = principal;
        this.curriculumList = curriculumDTOS;
    }
}
