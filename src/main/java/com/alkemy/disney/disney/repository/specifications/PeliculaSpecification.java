package com.alkemy.disney.disney.repository.specifications;

import com.alkemy.disney.disney.dto.PeliculaFilterDTO;
import com.alkemy.disney.disney.entity.PeliculaEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PeliculaSpecification {
    public Specification<PeliculaEntity> getByFilters(PeliculaFilterDTO filtersDTO) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasLength(filtersDTO.getName())){
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("titulo")),
                                "%" + filtersDTO.getName().toLowerCase() + "%"
                        )
                );
            }

            if (filtersDTO.getGenre()>0){
                predicates.add(
                        criteriaBuilder.equal(root.get("generoId"), filtersDTO.getGenre())
                );
            }

            //Remove duplicates
            query.distinct(true);

            //Order resolver
            String orderByField = "titulo";
            query.orderBy(

                    filtersDTO.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)) :
                            criteriaBuilder.desc(root.get(orderByField))
            );

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
