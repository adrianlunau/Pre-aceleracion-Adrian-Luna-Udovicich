package com.alkemy.disney.disney.service;

import com.alkemy.disney.disney.dto.PeliculaBasicDTO;
import com.alkemy.disney.disney.dto.PeliculaDTO;

import java.util.List;

public interface PeliculaService {
    PeliculaDTO save (PeliculaDTO dto);

    PeliculaDTO getDetails(Long id);

    PeliculaDTO update (PeliculaDTO dto);

    void delete (Long id);

    List<PeliculaBasicDTO> getBasicList ();
}
