package ca.bertsa.internos.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ca.bertsa.internos.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class UpdateStatusDTO {
    private Long idOfferApplied;
    private Status status;
}
