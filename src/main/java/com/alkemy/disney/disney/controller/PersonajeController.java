package com.alkemy.disney.disney.controller;

import com.alkemy.disney.disney.dto.PeliculaDTO;
import com.alkemy.disney.disney.dto.PersonajeBasicDTO;
import com.alkemy.disney.disney.dto.PersonajeDTO;
import com.alkemy.disney.disney.entity.PersonajeEntity;
import com.alkemy.disney.disney.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("characters")
public class PersonajeController {
    @Autowired
    private PersonajeService personajeService;

    @PostMapping
    public ResponseEntity<PersonajeDTO> save (@RequestBody  PersonajeDTO personaje) {
        PersonajeDTO personajeGuardado = personajeService.save(personaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(personajeGuardado);
    }

    @PutMapping
    public ResponseEntity<PersonajeDTO> update (@RequestBody PersonajeDTO personaje) {
        PersonajeDTO personajeModificado = personajeService.update(personaje);
        return ResponseEntity.ok().body(personajeModificado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.personajeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonajeDTO> getById(@PathVariable Long id) {
        PersonajeDTO dto = this.personajeService.getById(id);
        return ResponseEntity.ok().body(dto);
    }

    /*
    @GetMapping
    public ResponseEntity<List<PersonajeBasicDTO>> getBasicList() {
        List<PersonajeBasicDTO> dtos = this.personajeService.getBasicList();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

     */

    //Filtros combinados
    @GetMapping
    public ResponseEntity<List<PersonajeDTO>> getDetailsByFilters(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer edad,
            @RequestParam(required = false) List<Long> peliculas,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        List<PersonajeDTO> personajes = this.personajeService.getByFilters(nombre, edad, peliculas, order);
        return ResponseEntity.ok(personajes);
    }
}
