package com.alkemy.disney.disney.controller;

import com.alkemy.disney.disney.dto.PeliculaDTO;
import com.alkemy.disney.disney.dto.PersonajeBasicDTO;
import com.alkemy.disney.disney.dto.PersonajeDTO;
import com.alkemy.disney.disney.entity.PersonajeEntity;
import com.alkemy.disney.disney.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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


    //Devuelve una lista basica

    public ResponseEntity<List<PersonajeBasicDTO>> getBasicList() {
        List<PersonajeBasicDTO> dtos = this.personajeService.getBasicList();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    //Filtros combinados

    public ResponseEntity<List<PersonajeBasicDTO>> getDetailsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "-1") String age,
            @RequestParam(required = false) List<Long> movies,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        List<PersonajeBasicDTO> personajes = this.personajeService.getByFilters(name, age, movies, order);
        return ResponseEntity.ok(personajes);
    }

    // Deriva a getByFilters si la request trae parametros y sino trae deriva a getBasicList

    @GetMapping
    public ResponseEntity<List<PersonajeBasicDTO>> get(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "-1") String age,
            @RequestParam(required = false) List<Long> movies,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        if (Objects.isNull(name) && Objects.isNull(age) && CollectionUtils.isEmpty(movies)){
            return this.getBasicList();
        } else {
            return  this.getDetailsByFilters(name, age, movies, order);

        }
    }



}
