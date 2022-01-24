package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.dto.PeliculaBasicDTO;
import com.alkemy.disney.disney.dto.PeliculaDTO;
import com.alkemy.disney.disney.dto.PeliculaFilterDTO;
import com.alkemy.disney.disney.entity.PeliculaEntity;
import com.alkemy.disney.disney.exception.ParamNotFound;
import com.alkemy.disney.disney.mapper.PeliculaMapper;
import com.alkemy.disney.disney.repository.PeliculaRepository;
import com.alkemy.disney.disney.repository.specifications.PeliculaSpecification;
import com.alkemy.disney.disney.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PeliculaServiceImpl implements PeliculaService {

    @Autowired
    private PeliculaMapper peliculaMapper;

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private PeliculaSpecification peliculaSpecification;


    @Override
    public PeliculaDTO save(PeliculaDTO dto) {
        PeliculaEntity entity = peliculaMapper.peliculaDTO2Entity(dto, true);
        PeliculaEntity peliculaSaved = peliculaRepository.save(entity);
        PeliculaDTO result = peliculaMapper.peliculaEntity2DTO(peliculaSaved, true);
        return result;
    }

    @Override
    public PeliculaDTO getDetails(Long id) {
        Optional<PeliculaEntity> entity = this.peliculaRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("ID pelicula no valido");
        }
        PeliculaDTO dto = peliculaMapper.peliculaEntity2DTO(entity.get(), true);
        return dto;
    }

    @Override
    public PeliculaDTO update(PeliculaDTO dto) {
        PeliculaEntity pelicula2Modify = peliculaRepository.getById(dto.getId());
        pelicula2Modify.setImagen(dto.getImage());
        pelicula2Modify.setTitulo(dto.getName());
        pelicula2Modify.setFechaCreacion(peliculaMapper.string2LocalDate(dto.getDate()));
        pelicula2Modify.setCalificacion(dto.getRating());
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

    @Override
    public List<PeliculaBasicDTO> getByFilters(String name, String genre, String order) {
        Long genreLong = Long.parseLong(genre);
        PeliculaFilterDTO filtersDTO = new PeliculaFilterDTO(name, genreLong, order);
        List<PeliculaEntity> entities = this.peliculaRepository.findAll(this.peliculaSpecification.getByFilters(filtersDTO));
        List<PeliculaBasicDTO> dtos = this.peliculaMapper.peliculaEntityList2BasicDTO(entities);

        return dtos;
    }

}
