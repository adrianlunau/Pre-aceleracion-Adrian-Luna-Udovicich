package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.dto.GeneroDTO;
import com.alkemy.disney.disney.entity.GeneroEntity;
import com.alkemy.disney.disney.mapper.GeneroMapper;
import com.alkemy.disney.disney.repository.GeneroRepository;
import com.alkemy.disney.disney.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroMapper generoMapper;

    @Autowired
    private GeneroRepository generoRepository;

    @Override
    public GeneroDTO save(GeneroDTO dto) {
        GeneroEntity entity = generoMapper.generoDTO2Entity(dto);
        GeneroEntity entitySaved = generoRepository.save(entity);
        GeneroDTO result = generoMapper.generoEntity2DTO(entitySaved);
        return result;
    }
}
