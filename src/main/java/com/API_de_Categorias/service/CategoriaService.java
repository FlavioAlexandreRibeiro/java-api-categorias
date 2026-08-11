package com.API_de_Categorias.service;

import com.API_de_Categorias.dto.CategoriaRequestDTO;
import com.API_de_Categorias.dto.CategoriaResponseDTO;
import com.API_de_Categorias.entity.Categoria;
import com.API_de_Categorias.exception.CategoriaDuplicadaException;
import com.API_de_Categorias.exception.CategoriaNaoEncontradaException;
import com.API_de_Categorias.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaResponseDTO cadastrar(CategoriaRequestDTO categoriaDTO) {

        if (categoriaRepository.existsByNomeIgnoreCase(
                categoriaDTO.getNome())) {
            throw new CategoriaDuplicadaException("Categoria com nome '"
                    + categoriaDTO.getNome() +
                    "' já existe");
        }

        Categoria categoria = new Categoria();

        categoria.setNome(categoriaDTO.getNome());

        Categoria categoriaSalva = categoriaRepository.save(categoria);

        return CategoriaResponseDTO.mapearParaDto(categoriaSalva);
    }


    public CategoriaResponseDTO buscarPorId(Long id) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(
                        "Categoria com ID " + id + " não encontrada."));

        return CategoriaResponseDTO.mapearParaDto(categoria);
    }


    public List<CategoriaResponseDTO> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaResponseDTO::mapearParaDto)
                .toList();
    }


    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO categoriaDTO) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(
                        "Categoria não encontrada"));

        if (categoriaRepository.existsByNomeIgnoreCaseAndIdNot(
                categoriaDTO.getNome(), id)) {

            throw new CategoriaDuplicadaException(
                    "Categoria com nome '" + categoriaDTO.getNome() + "' já existe.");
        }

        categoria.setNome(categoriaDTO.getNome());

        Categoria categoriaAtualizada = categoriaRepository.save(categoria);

        return CategoriaResponseDTO.mapearParaDto(categoriaAtualizada);
    }


    public void deletar(Long id) {

        if (!categoriaRepository.existsById(id)) {
            throw new CategoriaNaoEncontradaException(
                    "Categoria com ID " + id + " não encontrada."
            );
        }

        categoriaRepository.deleteById(id);
    }


    public List<CategoriaResponseDTO> buscarPorNome(String nome) {
        return categoriaRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(CategoriaResponseDTO::mapearParaDto)
                .toList();
    }


    public boolean existePorId(Long id) {
        return categoriaRepository.existsById(id);
    }
}
