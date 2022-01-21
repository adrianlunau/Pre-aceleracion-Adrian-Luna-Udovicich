package com.alkemy.disney.disney.mapper;

import com.alkemy.disney.disney.dto.PeliculaDTO;
import com.alkemy.disney.disney.dto.PersonajeBasicDTO;
import com.alkemy.disney.disney.dto.PersonajeDTO;
import com.alkemy.disney.disney.entity.PersonajeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonajeMapper {


    private PeliculaMapper peliculaMapper;

    public PersonajeEntity personajeDTO2Entity (PersonajeDTO dto) {
        PersonajeEntity entity = new PersonajeEntity();
        //aca agregue el id
        //entity.setId(dto.getId());
        entity.setImagen(dto.getImagen());
        entity.setNombre(dto.getNombre());
        entity.setEdad(dto.getEdad());
        entity.setPeso(dto.getPeso());
        entity.setHistoria(dto.getHistoria());
        return entity;
    }

    public PersonajeDTO personajeEntity2DTO (PersonajeEntity entity, boolean loadPeliculas) {
        PersonajeDTO dto = new PersonajeDTO();
        dto.setId(entity.getId());
        dto.setImagen(entity.getImagen());
        dto.setNombre(entity.getNombre());
        dto.setEdad(entity.getEdad());
        dto.setPeso(entity.getPeso());
        dto.setHistoria(entity.getHistoria());
        if (loadPeliculas){
            List<PeliculaDTO> peliculasDTO = this.peliculaMapper.peliculasEntityList2DTOList(entity.getPeliculas(), false);
        }
        return dto;
    }

    public List<PersonajeEntity> personajeDTOList2entities (List<PersonajeDTO> dtos) {
        List<PersonajeEntity> entities = new ArrayList<>();
        for (PersonajeDTO dto : dtos){
            entities.add(this.personajeDTO2Entity(dto));
        }
        return entities;
    }

    public List<PersonajeDTO> personajeEntityList2DTOList (List<PersonajeEntity> entities) {
        List<PersonajeDTO> dtos = new ArrayList<>();
        for (PersonajeEntity entity : entities) {
            dtos.add(this.personajeEntity2DTO(entity,false));
        }
        return dtos;
    }

    public List<PersonajeBasicDTO> personajeEntityList2BasicDTOList (List<PersonajeEntity> entities){
        List<PersonajeBasicDTO> dtos = new ArrayList<>();
        PersonajeBasicDTO basicDTO;
        for (PersonajeEntity entity : entities){
            basicDTO = new PersonajeBasicDTO();
            basicDTO.setId(entity.getId());
            basicDTO.setImagen(entity.getImagen());
            basicDTO.setNombre(entity.getNombre());
            dtos.add(basicDTO);
        }
        return dtos;
    }
}
