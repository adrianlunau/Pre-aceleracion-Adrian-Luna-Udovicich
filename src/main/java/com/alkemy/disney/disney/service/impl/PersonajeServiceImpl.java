package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.dto.PersonajeBasicDTO;
import com.alkemy.disney.disney.dto.PersonajeDTO;
import com.alkemy.disney.disney.dto.PersonajeFilterDTO;
import com.alkemy.disney.disney.entity.PersonajeEntity;
import com.alkemy.disney.disney.exception.ParamNotFound;
import com.alkemy.disney.disney.mapper.PersonajeMapper;
import com.alkemy.disney.disney.repository.PersonajeRepository;
import com.alkemy.disney.disney.repository.specifications.PersonajeSpecification;
import com.alkemy.disney.disney.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    @Autowired
    private PersonajeMapper personajeMapper;

    @Autowired
    private PersonajeRepository personajeRepository;

    @Autowired
    private PersonajeSpecification personajeSpecification;



    @Override
    public PersonajeDTO save(PersonajeDTO dto) {
        PersonajeEntity entity = personajeMapper.personajeDTO2Entity(dto);
        PersonajeEntity entitySaved = this.personajeRepository.save(entity);
        PersonajeDTO result = personajeMapper.personajeEntity2DTO(entitySaved, false);
        return result;
    }

    @Override
    public PersonajeDTO update(PersonajeDTO personaje) {
        Optional<PersonajeEntity> personaje2Modified = personajeRepository.findById(personaje.getId());

        if(!personaje2Modified.isPresent()){
            throw new ParamNotFound("ID personaje no valido");
        }
        personaje2Modified.get().setImagen(personaje.getImagen());
        personaje2Modified.get().setNombre(personaje.getNombre());
        personaje2Modified.get().setEdad(personaje.getEdad());
        personaje2Modified.get().setPeso(personaje.getPeso());
        personaje2Modified.get().setHistoria(personaje.getHistoria());
        PersonajeEntity personajeSaved = personajeRepository.save(personaje2Modified.get());

        PersonajeDTO result = personajeMapper.personajeEntity2DTO(personajeSaved, false);
        return result;
    }

    @Override
    public void delete(Long id) {
        Optional<PersonajeEntity> entity = this.personajeRepository.findById(id);
        if (!entity.isPresent()){
            throw new ParamNotFound("ID personaje no valido");
        }
        this.personajeRepository.deleteById(id);
    }

    @Override
    public PersonajeDTO getById(Long id) {
        Optional<PersonajeEntity> entity = this.personajeRepository.findById(id);
        if (!entity.isPresent()){
            throw new ParamNotFound("ID personaje no valido");
        }
        PersonajeDTO result = this.personajeMapper.personajeEntity2DTO(entity.get(), true);
        return result;
    }

    @Override
    public List<PersonajeBasicDTO> getBasicList() {
        List<PersonajeEntity> personajes = this.personajeRepository.findAll();
        List<PersonajeBasicDTO> personajesDTOList = this.personajeMapper.personajeEntityList2BasicDTOList(personajes);
        return personajesDTOList;
    }

    @Override
    public List<PersonajeBasicDTO> getByFilters(String nombre, String edad, List<Long> peliculas, String order) {

        Integer edadInt = Integer.parseInt(edad);

        PersonajeFilterDTO filtersDTO = new PersonajeFilterDTO(nombre, edadInt, peliculas, order);
        List<PersonajeEntity> entities = this.personajeRepository.findAll(this.personajeSpecification.getByFilters(filtersDTO));
        List<PersonajeBasicDTO> dtos = this.personajeMapper.personajeEntityList2BasicDTOList(entities);
        return dtos;
    }
}
