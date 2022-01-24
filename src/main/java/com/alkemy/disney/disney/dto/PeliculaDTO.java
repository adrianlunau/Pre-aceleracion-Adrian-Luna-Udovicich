package com.alkemy.disney.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PeliculaDTO {
    private Long id;
    private String image;
    private String name;
    private String date;
    private Long genre;
    private int rating;
    private List<PersonajeDTO> characters = new ArrayList<>();
}
