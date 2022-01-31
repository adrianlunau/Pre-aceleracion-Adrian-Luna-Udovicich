package com.alkemy.disney.disney.controller;

import com.alkemy.disney.disney.dto.PeliculaBasicDTO;
import com.alkemy.disney.disney.dto.PeliculaDTO;
import com.alkemy.disney.disney.dto.PersonajeDTO;
import com.alkemy.disney.disney.entity.PersonajeEntity;
import com.alkemy.disney.disney.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("movies")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @PostMapping
    public ResponseEntity<PeliculaDTO> save (@Valid @RequestBody PeliculaDTO pelicula) {
        PeliculaDTO peliculaSaved = peliculaService.save(pelicula);
        return ResponseEntity.status(HttpStatus.CREATED).body(peliculaSaved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeliculaDTO> getDetails (@PathVariable Long id) {
        PeliculaDTO pelicula = peliculaService.getDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(pelicula);
    }

    @PutMapping
    public ResponseEntity<PeliculaDTO> update (@Valid @RequestBody PeliculaDTO pelicula) {
        PeliculaDTO peliculaModified = peliculaService.update(pelicula);
        return ResponseEntity.status(HttpStatus.OK).body(peliculaModified);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        this.peliculaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping
    public ResponseEntity<List<PeliculaBasicDTO>> getByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") String genre,
            @RequestParam(required = false, defaultValue = "ASC") String order) {

        List<PeliculaBasicDTO> peliculas = this.peliculaService.getByFilters(name, genre, order);
        return ResponseEntity.ok(peliculas);

    }


    @PostMapping("/{id}")
    public ResponseEntity<PeliculaDTO> addCharacter (@PathVariable Long id,@Valid @RequestBody PersonajeDTO personaje) {
        PeliculaDTO dto = this.peliculaService.addCharacter(id, personaje);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping("/{id}/{idPersonaje}")
    public ResponseEntity<PeliculaDTO> removeCharacter (@PathVariable Long id, @PathVariable Long idPersonaje) {
        PeliculaDTO dto = this.peliculaService.removeCharacter(id, idPersonaje);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

}
