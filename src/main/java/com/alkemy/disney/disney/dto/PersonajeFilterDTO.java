package com.alkemy.disney.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonajeFilterDTO {
    private String nombre;
    private Integer edad;
    private List<Long> peliculas;
    private String order;

    public PersonajeFilterDTO(String nombre, Integer edad, List<Long> peliculas, String order) {
        this.nombre = nombre;
        this.edad = edad;
        this.peliculas = peliculas;
        this.order = order;
    }

    public boolean isASC() {
        return this.order.compareToIgnoreCase("ASC") == 0;
    }

    public boolean isDESC() {
        return this.order.compareToIgnoreCase("DESC") == 0;
    }
}
