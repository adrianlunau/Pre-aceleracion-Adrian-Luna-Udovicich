package com.alkemy.disney.disney.controller;

import com.alkemy.disney.disney.dto.PeliculaDTO;
import com.alkemy.disney.disney.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movies")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @PostMapping
    public ResponseEntity<PeliculaDTO> save (@RequestBody PeliculaDTO pelicula) {
        PeliculaDTO peliculaSaved = peliculaService.save(pelicula);
        return ResponseEntity.status(HttpStatus.CREATED).body(peliculaSaved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeliculaDTO> getDetails (@PathVariable Long id) {
        PeliculaDTO pelicula = peliculaService.getDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(pelicula);
    }

    @PutMapping
    public ResponseEntity<PeliculaDTO> update (@RequestBody PeliculaDTO pelicula) {
        PeliculaDTO peliculaModified = peliculaService.update(pelicula);
        return ResponseEntity.status(HttpStatus.OK).body(peliculaModified);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        this.peliculaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
