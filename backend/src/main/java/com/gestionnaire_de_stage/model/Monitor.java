package com.gestionnaire_de_stage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Monitor extends User {

    @NotNull
    private String department;

    private String address;

    private String city;

    private String postalCode;
}
