package com.alura.literalura.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosGutendex(
        @JsonAlias("count") Integer conta,
        @JsonAlias("next") String proximo,
        @JsonAlias("previous") String anterior,
        @JsonAlias("results") List<LivroDTO> resultados
) {
}
