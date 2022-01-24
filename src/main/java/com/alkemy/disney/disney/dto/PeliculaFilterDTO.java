package com.alkemy.disney.disney.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeliculaFilterDTO {
    private String name;
    private Long genre;
    private String order;

    public PeliculaFilterDTO(String name, Long genreId, String order) {
        this.name = name;
        this.genre = genreId;
        this.order = order;
    }

    public boolean isASC() {
        return this.order.compareToIgnoreCase("ASC") == 0;
    }

    public boolean isDESC() {
        return this.order.compareToIgnoreCase("DESC") == 0;
    }
}
