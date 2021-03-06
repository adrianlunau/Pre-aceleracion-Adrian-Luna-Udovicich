package com.alkemy.disney.disney.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PersonajeDTO {
    private Long id;
    private String imagen;
    @NotNull
    private String nombre;

    private Integer edad;
    @Positive
    private double peso;
    private String historia;
    private List<PeliculaDTO> peliculas = new ArrayList<>();
}
