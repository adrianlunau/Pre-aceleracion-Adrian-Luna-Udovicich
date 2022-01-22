package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.dto.PeliculaBasicDTO;
import com.alkemy.disney.disney.dto.PeliculaDTO;
import com.alkemy.disney.disney.entity.PeliculaEntity;
import com.alkemy.disney.disney.mapper.PeliculaMapper;
import com.alkemy.disney.disney.repository.PeliculaRepository;
import com.alkemy.disney.disney.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaServiceImpl implements PeliculaService {

    @Autowired
    private PeliculaMapper peliculaMapper;

    @Autowired
    private PeliculaRepository peliculaRepository;


    @Override
    public PeliculaDTO save(PeliculaDTO dto) {
        PeliculaEntity entity = peliculaMapper.peliculaDTO2Entity(dto, true);
        PeliculaEntity peliculaSaved = peliculaRepository.save(entity);
        PeliculaDTO result = peliculaMapper.peliculaEntity2DTO(peliculaSaved, true);
        return result;
    }

    @Override
    public PeliculaDTO getDetails(Long id) {
        PeliculaEntity entity = peliculaRepository.getById(id);
        PeliculaDTO dto = peliculaMapper.peliculaEntity2DTO(entity, true);
        return dto;
    }

    @Override
    public PeliculaDTO update(PeliculaDTO dto) {
        PeliculaEntity pelicula2Modify = peliculaRepository.getById(dto.getId());
        pelicula2Modify.setImagen(dto.getImagen());
        pelicula2Modify.setTitulo(dto.getTitulo());
        pelicula2Modify.setFechaCreacion(peliculaMapper.string2LocalDate(dto.getFechaCreacion()));
        pelicula2Modify.setCalificacion(dto.getCalificacion());
        peliculaRepository.save(pelicula2Modify);

        PeliculaDTO result = peliculaMapper.peliculaEntity2DTO(pelicula2Modify, true);

        return result;
    }

    @Override
    public void delete(Long id) {
        this.peliculaRepository.deleteById(id);
    }

    @Override
    public List<PeliculaBasicDTO> getBasicList() {

        List<PeliculaEntity> entities = this.peliculaRepository.findAll();
        List<PeliculaBasicDTO> result = this.peliculaMapper.peliculaEntityList2BasicDTO(entities);
        return result;
    }

}
