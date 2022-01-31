package com.alkemy.disney.disney.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PersonajeDTO {
    private Long id;
    private String imagen;
    @NotNull
    private String nombre;
    @Min(0)
    private Integer edad;
    @Min(0)
    private double peso;
    private String historia;
    private List<PeliculaDTO> peliculas = new ArrayList<>();
}
