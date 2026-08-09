package com.API_de_Categorias.dto;

import com.API_de_Categorias.entity.Categoria;

public record CategoriaResponseDTO (

        Long id,
        String nome
) {

    public static CategoriaResponseDTO mapearParaDto(Categoria categoria) {

        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome()
        );
    }
}
