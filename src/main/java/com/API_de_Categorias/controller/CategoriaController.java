package com.API_de_Categorias.controller;

import com.API_de_Categorias.dto.CategoriaRequestDTO;
import com.API_de_Categorias.dto.CategoriaResponseDTO;
import com.API_de_Categorias.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;


    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(
            @Valid @RequestBody CategoriaRequestDTO categoria) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.cadastrar(categoria));
    }


    @GetMapping
    public List<CategoriaResponseDTO> listar(
            @RequestParam(required = false) String nome) {

        if (nome == null) {
            return categoriaService.listar();
    }
        return categoriaService.buscarPorNome(nome);
    }


    @GetMapping("/{id}")
    public CategoriaResponseDTO buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDTO categoria) {

        return ResponseEntity.ok(categoriaService.atualizar(id, categoria));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/existe")
    public boolean existePorId(@PathVariable Long id) {
        return categoriaService.existePorId(id);
    }
}
