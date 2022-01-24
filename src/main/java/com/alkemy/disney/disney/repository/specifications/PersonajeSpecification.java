package com.alkemy.disney.disney.repository.specifications;

import com.alkemy.disney.disney.dto.PersonajeFilterDTO;
import com.alkemy.disney.disney.entity.PeliculaEntity;
import com.alkemy.disney.disney.entity.PersonajeEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PersonajeSpecification {

    public Specification<PersonajeEntity> getByFilters(PersonajeFilterDTO filtersDTO) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasLength(filtersDTO.getNombre())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("nombre")),
                                "%" + filtersDTO.getNombre().toLowerCase() + "%"
                        )
                );
            }

            // dentro del if tenia esto: !Objects.isNull(filtersDTO.getEdad())
            if (filtersDTO.getEdad() >= 0) {

                predicates.add(
                        criteriaBuilder.equal(root.get("edad"), filtersDTO.getEdad())
                );
            }

            if (!CollectionUtils.isEmpty(filtersDTO.getPeliculas())) {
                Join<PersonajeEntity, PeliculaEntity> join = root.join("peliculas", JoinType.INNER);
                Expression<String> peliculasId = join.get("id");
                predicates.add(peliculasId.in(filtersDTO.getPeliculas()));
            }

            //Remove duplicates
            query.distinct(true);

            //Order resolver
            String orderByField = "nombre";
            query.orderBy(

                    filtersDTO.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)) :
                            criteriaBuilder.desc(root.get(orderByField))
            );

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
