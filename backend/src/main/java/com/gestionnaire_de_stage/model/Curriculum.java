package com.gestionnaire_de_stage.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
public class Curriculum implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;

    private String type;

    @Lob
    private byte[] data;

    @NotNull
    @ManyToOne
    private Student student;

    private Boolean isValid;

    public Curriculum(String name, String type, byte[] data, Student student) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.student = student;
        this.isValid = null;
    }
}
