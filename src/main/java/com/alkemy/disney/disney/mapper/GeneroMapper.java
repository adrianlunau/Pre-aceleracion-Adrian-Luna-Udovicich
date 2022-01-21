package com.alkemy.disney.disney.mapper;

import com.alkemy.disney.disney.dto.GeneroDTO;
import com.alkemy.disney.disney.entity.GeneroEntity;
import org.springframework.stereotype.Component;

@Component
public class GeneroMapper {

    public GeneroEntity generoDTO2Entity (GeneroDTO dto){
        GeneroEntity entity = new GeneroEntity();
        entity.setImagen(dto.getImagen());
        entity.setNombre(dto.getNombre());
        return entity;
    }

    public GeneroDTO generoEntity2DTO (GeneroEntity entity) {
        GeneroDTO dto = new GeneroDTO();
        dto.setId(entity.getId());
        dto.setImagen(entity.getImagen());
        dto.setNombre(entity.getNombre());
        return dto;
    }
}
