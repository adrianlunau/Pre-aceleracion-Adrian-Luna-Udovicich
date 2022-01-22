package com.alkemy.disney.disney.service;

import com.alkemy.disney.disney.dto.PersonajeBasicDTO;
import com.alkemy.disney.disney.dto.PersonajeDTO;

import java.util.List;


public interface PersonajeService {

    PersonajeDTO save (PersonajeDTO dto);

    PersonajeDTO update (PersonajeDTO dto);

    void delete (Long id);

    PersonajeDTO getById (Long id);

    List<PersonajeBasicDTO> getBasicList();

    List<PersonajeDTO> getByFilters(String nombre, int edad, List<Long> peliculas, String order);
}
