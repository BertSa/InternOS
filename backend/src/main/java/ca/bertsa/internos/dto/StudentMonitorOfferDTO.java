package ca.bertsa.internos.dto;

import ca.bertsa.internos.model.Monitor;
import ca.bertsa.internos.model.Offer;
import ca.bertsa.internos.model.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentMonitorOfferDTO {

    private Student student;

    private Monitor monitor;

    private Offer offer;

}
