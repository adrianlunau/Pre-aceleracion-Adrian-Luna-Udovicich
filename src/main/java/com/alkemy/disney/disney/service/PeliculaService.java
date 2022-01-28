package com.alkemy.disney.disney.service;

import com.alkemy.disney.disney.dto.PeliculaBasicDTO;
import com.alkemy.disney.disney.dto.PeliculaDTO;
import com.alkemy.disney.disney.dto.PersonajeDTO;

import java.util.List;

public interface PeliculaService {
    PeliculaDTO save (PeliculaDTO dto);

    PeliculaDTO getDetails(Long id);

    PeliculaDTO update (PeliculaDTO dto);

    void delete (Long id);

    List<PeliculaBasicDTO> getBasicList ();

    List<PeliculaBasicDTO> getByFilters(String name, String genre, String order);

    PeliculaDTO addCharacter(Long id, PersonajeDTO personaje);

    PeliculaDTO removeCharacter(Long id, Long idPersonaje);
}
