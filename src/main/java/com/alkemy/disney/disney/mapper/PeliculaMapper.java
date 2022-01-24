package com.alkemy.disney.disney.mapper;

import com.alkemy.disney.disney.dto.PeliculaBasicDTO;
import com.alkemy.disney.disney.dto.PeliculaDTO;
import com.alkemy.disney.disney.dto.PersonajeDTO;
import com.alkemy.disney.disney.entity.PeliculaEntity;
import com.alkemy.disney.disney.entity.PersonajeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class PeliculaMapper {

    @Autowired
    @Lazy
    private PersonajeMapper personajeMapper;



    public PeliculaEntity peliculaDTO2Entity(PeliculaDTO dto, boolean loadPersonajes) {
        PeliculaEntity entity = new PeliculaEntity();
        entity.setImagen(dto.getImage());
        entity.setTitulo(dto.getName());
        entity.setFechaCreacion(this.string2LocalDate(dto.getDate()));
        entity.setCalificacion(dto.getRating());
        entity.setGeneroId(dto.getGenre());
        if (loadPersonajes){
            List<PersonajeEntity> personajesEntityList = this.personajeMapper.personajeDTOList2entities(dto.getCharacters());
            entity.setPersonajes(personajesEntityList);
        }
        return entity;
    }
    public PeliculaDTO peliculaEntity2DTO(PeliculaEntity entity, boolean loadPersonajes) {
        PeliculaDTO dto = new PeliculaDTO();
        dto.setId(entity.getId());
        dto.setImage(entity.getImagen());
        dto.setName(entity.getTitulo());
        dto.setDate(entity.getFechaCreacion().toString());
        dto.setRating(entity.getCalificacion());
        if (loadPersonajes){
            List<PersonajeDTO> personajes = this.personajeMapper.personajeEntityList2DTOList(entity.getPersonajes());
            dto.setCharacters(personajes);

        }
        return dto;
    }

    public List<PeliculaDTO> peliculasEntityList2DTOList(List<PeliculaEntity> entities, boolean loadPersonajes) {
        List<PeliculaDTO> dtos = new ArrayList<>();
        for (PeliculaEntity entity : entities) {
            dtos.add(this.peliculaEntity2DTO(entity, loadPersonajes));
        }
        return dtos;
    }

    public List<PeliculaEntity> peliculasDTOList2EntityList (List<PeliculaDTO> dtos, boolean loadPersonajes) {
        List<PeliculaEntity> entities = new ArrayList<>();
        for (PeliculaDTO dto : dtos) {
            entities.add(this.peliculaDTO2Entity(dto, loadPersonajes));
        }
        return entities;
    }

    public List<PeliculaBasicDTO> peliculaEntityList2BasicDTO (List<PeliculaEntity> entities){
        List<PeliculaBasicDTO> listBasicDTOS = new ArrayList<>();
        PeliculaBasicDTO basicDTO;
        for (PeliculaEntity entity : entities){
            basicDTO = new PeliculaBasicDTO();

            basicDTO.setImage(entity.getImagen());
            basicDTO.setName(entity.getTitulo());
            basicDTO.setDate(entity.getFechaCreacion().toString());
            listBasicDTOS.add(basicDTO);
        }
        return listBasicDTOS;
    }

    //String to LocalDate
    public LocalDate string2LocalDate (String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(stringDate, formatter);
        return date;
    }

}
