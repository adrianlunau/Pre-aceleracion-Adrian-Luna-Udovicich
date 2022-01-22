package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.dto.PersonajeBasicDTO;
import com.alkemy.disney.disney.dto.PersonajeDTO;
import com.alkemy.disney.disney.dto.PersonajeFilterDTO;
import com.alkemy.disney.disney.entity.PersonajeEntity;
import com.alkemy.disney.disney.mapper.PersonajeMapper;
import com.alkemy.disney.disney.repository.PersonajeRepository;
import com.alkemy.disney.disney.repository.specifications.PersonajeSpecification;
import com.alkemy.disney.disney.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public PersonajeDTO update(PersonajeDTO personaje) {
        PersonajeEntity personaje2Modified = personajeRepository.getById(personaje.getId());
        personaje2Modified.setImagen(personaje.getImagen());
        personaje2Modified.setNombre(personaje.getNombre());
        personaje2Modified.setEdad(personaje.getEdad());
        personaje2Modified.setPeso(personaje.getPeso());
        personaje2Modified.setHistoria(personaje.getHistoria());
        personajeRepository.save(personaje2Modified);

        PersonajeDTO result = personajeMapper.personajeEntity2DTO(personaje2Modified, false);
        return result;
    }

    @Override
    public void delete(Long id) {
        this.personajeRepository.deleteById(id);
    }

    @Override
    public PersonajeDTO getById(Long id) {
        PersonajeEntity entity = this.personajeRepository.getById(id);
        PersonajeDTO result = this.personajeMapper.personajeEntity2DTO(entity, true);
        return result;
    }

    @Override
    public List<PersonajeBasicDTO> getBasicList() {
        List<PersonajeEntity> personajes = this.personajeRepository.findAll();
        List<PersonajeBasicDTO> personajesDTOList = this.personajeMapper.personajeEntityList2BasicDTOList(personajes);
        return personajesDTOList;
    }

    @Override
    public List<PersonajeDTO> getByFilters(String nombre, int edad, List<Long> peliculas, String order) {
        PersonajeFilterDTO filtersDTO = new PersonajeFilterDTO(nombre, edad, peliculas, order);
        List<PersonajeEntity> entities = this.personajeRepository.findAll(this.personajeSpecification.getByFilters(filtersDTO));
        List<PersonajeDTO> dtos = this.personajeMapper.personajeEntityList2DTOList(entities);
        return dtos;
    }
}
