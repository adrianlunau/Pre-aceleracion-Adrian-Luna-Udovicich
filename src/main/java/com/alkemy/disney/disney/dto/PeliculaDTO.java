package com.alkemy.disney.disney.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
public class PeliculaDTO {
    private Long id;
    private String imagen;
    @NotNull
    private String titulo;
    @Pattern(regexp= "\\d{4}-\\d{2}-\\d{2}" , message= "El formato de fecha debe ser yyyy-MM-dd" )
    private String fechaCreacion;
    private Long genero;
    @Min(1)
    @Max(5)
    private int calificacion;
    private List<PersonajeDTO> personajes = new ArrayList<>();

}
