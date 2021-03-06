package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.dto.PeliculaBasicDTO;
import com.alkemy.disney.disney.dto.PeliculaDTO;
import com.alkemy.disney.disney.dto.PeliculaFilterDTO;
import com.alkemy.disney.disney.dto.PersonajeDTO;
import com.alkemy.disney.disney.entity.PeliculaEntity;
import com.alkemy.disney.disney.entity.PersonajeEntity;
import com.alkemy.disney.disney.exception.ErrorEnum;
import com.alkemy.disney.disney.exception.ParamNotFound;
import com.alkemy.disney.disney.mapper.PeliculaMapper;
import com.alkemy.disney.disney.mapper.PersonajeMapper;
import com.alkemy.disney.disney.repository.PeliculaRepository;
import com.alkemy.disney.disney.repository.PersonajeRepository;
import com.alkemy.disney.disney.repository.specifications.PeliculaSpecification;
import com.alkemy.disney.disney.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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

    @Autowired
    @Lazy
    private PersonajeMapper personajeMapper;

    @Autowired
    @Lazy
    private PersonajeRepository personajeRepository;


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
            throw new ParamNotFound(ErrorEnum.IDPELINOTVALID.getMensaje());
        }
        PeliculaDTO dto = peliculaMapper.peliculaEntity2DTO(entity.get(), true);
        return dto;
    }

    @Override
    public PeliculaDTO update(PeliculaDTO dto) {
        Optional<PeliculaEntity> pelicula2Modify = peliculaRepository.findById(dto.getId());

        if (!pelicula2Modify.isPresent()) {
            throw new ParamNotFound(ErrorEnum.IDPELINOTVALID.getMensaje());
        }
        pelicula2Modify.get().setImagen(dto.getImagen());
        pelicula2Modify.get().setTitulo(dto.getTitulo());
        pelicula2Modify.get().setFechaCreacion(peliculaMapper.string2LocalDate(dto.getFechaCreacion()));
        pelicula2Modify.get().setCalificacion(dto.getCalificacion());
        PeliculaEntity peliculaSaved = peliculaRepository.save(pelicula2Modify.get());

        PeliculaDTO result = peliculaMapper.peliculaEntity2DTO(peliculaSaved, true);

        return result;
    }

    @Override
    public void delete(Long id) {
        Optional<PeliculaEntity> entity = this.peliculaRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound(ErrorEnum.IDPELINOTVALID.getMensaje());
        }
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

    // addCharacter service
    @Override
    public PeliculaDTO addCharacter(Long idPelicula, Long idPersonaje) {

        Optional<PeliculaEntity> optionalPelicula = this.peliculaRepository.findById(idPelicula);
        if (!optionalPelicula.isPresent()) {
            throw new ParamNotFound(ErrorEnum.IDPELINOTVALID.getMensaje());
        }

        Optional<PersonajeEntity> optionalPersonaje = this.personajeRepository.findById(idPersonaje);
        if (!optionalPersonaje.isPresent() || characterIsInMovie(idPelicula, idPersonaje)) {
            throw new ParamNotFound(ErrorEnum.IDPERSONAJENOTVALID.getMensaje());
        }


        PersonajeEntity personajeEntity = optionalPersonaje.get();
        PeliculaEntity peliculaEntity = optionalPelicula.get();

        peliculaEntity.addCharacter(personajeEntity);
        PeliculaEntity entitySaved = this.peliculaRepository.save(peliculaEntity);
        PeliculaDTO result = this.peliculaMapper.peliculaEntity2DTO(entitySaved, true);
        return result;
    }

    public Boolean characterIsInMovie(Long idPelicula, Long idPersonaje){
        Optional<PeliculaEntity> optionalPelicula = this.peliculaRepository.findById(idPelicula);
        List<PersonajeEntity> personajes = optionalPelicula.get().getPersonajes();
        for (PersonajeEntity personaje : personajes){
            if (personaje.getId() == idPersonaje){
                return true;
            }
        }
        return false;
    }

    @Override
    public PeliculaDTO removeCharacter(Long id, Long idPersonaje) {
        Optional<PeliculaEntity> pelicula = this.peliculaRepository.findById(id);

        if (!pelicula.isPresent()) {
            throw new ParamNotFound(ErrorEnum.IDPELINOTVALID.getMensaje());
        }
        PeliculaEntity peliculaEntity = pelicula.get();


        Optional<PersonajeEntity> personajeOptional = this.personajeRepository.findById(idPersonaje);
        if (!personajeOptional.isPresent()) {
            throw new ParamNotFound(ErrorEnum.IDPERSONAJENOTVALID.getMensaje());
        }

        PersonajeEntity personaje = personajeOptional.get();

        if (!peliculaEntity.getPersonajes().contains(personaje)) {
            throw new ParamNotFound(ErrorEnum.IDPERSONAJENOTVALID.getMensaje());
        }

        peliculaEntity.removeCharacter(personaje);

        PeliculaEntity entitySaved = this.peliculaRepository.save(peliculaEntity);
        PeliculaDTO result = this.peliculaMapper.peliculaEntity2DTO(entitySaved, true);
        return result;
    }

}
