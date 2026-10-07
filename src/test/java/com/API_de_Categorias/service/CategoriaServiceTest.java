package com.API_de_Categorias.service;

import com.API_de_Categorias.dto.CategoriaRequestDTO;
import com.API_de_Categorias.dto.CategoriaResponseDTO;
import com.API_de_Categorias.entity.Categoria;
import com.API_de_Categorias.exception.CategoriaDuplicadaException;
import com.API_de_Categorias.exception.CategoriaNaoEncontradaException;
import com.API_de_Categorias.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoria;
    private CategoriaRequestDTO categoriaRequestDTO;
    private CategoriaResponseDTO categoriaResponseDTO;

    @BeforeEach
    void setUp() {
        categoria = new Categoria(1L, "Eletrônicos");
        categoriaRequestDTO = new CategoriaRequestDTO();
        categoriaRequestDTO.setNome("Eletrônicos");
        categoriaResponseDTO = new CategoriaResponseDTO(1L, "Eletrônicos");
    }

    @Test
    void cadastrar_DeveRetornarCategoriaResponseDTO_QuandoNomeNaoExiste() {
        when(categoriaRepository.existsByNomeIgnoreCase("Eletrônicos")).thenReturn(false);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        CategoriaResponseDTO resultado = categoriaService.cadastrar(categoriaRequestDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("Eletrônicos", resultado.nome());
        verify(categoriaRepository, times(1)).existsByNomeIgnoreCase("Eletrônicos");
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void cadastrar_DeveLancarCategoriaDuplicadaException_QuandoNomeJaExiste() {
        when(categoriaRepository.existsByNomeIgnoreCase("Eletrônicos")).thenReturn(true);

        CategoriaDuplicadaException exception = assertThrows(
                CategoriaDuplicadaException.class,
                () -> categoriaService.cadastrar(categoriaRequestDTO)
        );

        assertEquals("Categoria com nome 'Eletrônicos' já existe", exception.getMessage());
        verify(categoriaRepository, times(1)).existsByNomeIgnoreCase("Eletrônicos");
        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void buscarPorId_DeveRetornarCategoriaResponseDTO_QuandoCategoriaExiste() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        CategoriaResponseDTO resultado = categoriaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("Eletrônicos", resultado.nome());
        verify(categoriaRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_DeveLancarCategoriaNaoEncontradaException_QuandoCategoriaNaoExiste() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        CategoriaNaoEncontradaException exception = assertThrows(
                CategoriaNaoEncontradaException.class,
                () -> categoriaService.buscarPorId(1L)
        );

        assertEquals("Categoria com ID 1 não encontrada.", exception.getMessage());
        verify(categoriaRepository, times(1)).findById(1L);
    }

    @Test
    void listar_DeveRetornarListaDeCategorias() {
        Categoria categoria2 = new Categoria(2L, "Livros");
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria, categoria2));

        List<CategoriaResponseDTO> resultado = categoriaService.listar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).id());
        assertEquals("Eletrônicos", resultado.get(0).nome());
        assertEquals(2L, resultado.get(1).id());
        assertEquals("Livros", resultado.get(1).nome());
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    void atualizar_DeveRetornarCategoriaResponseDTO_QuandoCategoriaExisteENomeNaoDuplicado() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.existsByNomeIgnoreCaseAndIdNot("Eletrônicos Atualizado", 1L)).thenReturn(false);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        CategoriaRequestDTO novoDTO = new CategoriaRequestDTO();
        novoDTO.setNome("Eletrônicos Atualizado");

        CategoriaResponseDTO resultado = categoriaService.atualizar(1L, novoDTO);

        assertNotNull(resultado);
        verify(categoriaRepository, times(1)).findById(1L);
        verify(categoriaRepository, times(1)).existsByNomeIgnoreCaseAndIdNot("Eletrônicos Atualizado", 1L);
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void atualizar_DeveLancarCategoriaNaoEncontradaException_QuandoCategoriaNaoExiste() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        CategoriaNaoEncontradaException exception = assertThrows(
                CategoriaNaoEncontradaException.class,
                () -> categoriaService.atualizar(1L, categoriaRequestDTO)
        );

        assertEquals("Categoria não encontrada", exception.getMessage());
        verify(categoriaRepository, times(1)).findById(1L);
        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void atualizar_DeveLancarCategoriaDuplicadaException_QuandoNomeJaExisteEmOutraCategoria() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.existsByNomeIgnoreCaseAndIdNot("Livros", 1L)).thenReturn(true);

        CategoriaRequestDTO novoDTO = new CategoriaRequestDTO();
        novoDTO.setNome("Livros");

        CategoriaDuplicadaException exception = assertThrows(
                CategoriaDuplicadaException.class,
                () -> categoriaService.atualizar(1L, novoDTO)
        );

        assertEquals("Categoria com nome 'Livros' já existe.", exception.getMessage());
        verify(categoriaRepository, times(1)).findById(1L);
        verify(categoriaRepository, times(1)).existsByNomeIgnoreCaseAndIdNot("Livros", 1L);
        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void deletar_DeveDeletarCategoria_QuandoCategoriaExiste() {
        when(categoriaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(categoriaRepository).deleteById(1L);

        categoriaService.deletar(1L);

        verify(categoriaRepository, times(1)).existsById(1L);
        verify(categoriaRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletar_DeveLancarCategoriaNaoEncontradaException_QuandoCategoriaNaoExiste() {
        when(categoriaRepository.existsById(1L)).thenReturn(false);

        CategoriaNaoEncontradaException exception = assertThrows(
                CategoriaNaoEncontradaException.class,
                () -> categoriaService.deletar(1L)
        );

        assertEquals("Categoria com ID 1 não encontrada.", exception.getMessage());
        verify(categoriaRepository, times(1)).existsById(1L);
        verify(categoriaRepository, never()).deleteById(anyLong());
    }

    @Test
    void buscarPorNome_DeveRetornarListaDeCategorias_QuandoNomeContemTermo() {
        Categoria categoria2 = new Categoria(2L, "Eletrônicos Portáteis");
        when(categoriaRepository.findByNomeContainingIgnoreCase("eletrônicos"))
                .thenReturn(List.of(categoria, categoria2));

        List<CategoriaResponseDTO> resultado = categoriaService.buscarPorNome("eletrônicos");

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Eletrônicos", resultado.get(0).nome());
        assertEquals("Eletrônicos Portáteis", resultado.get(1).nome());
        verify(categoriaRepository, times(1)).findByNomeContainingIgnoreCase("eletrônicos");
    }

    @Test
    void buscarPorNome_DeveRetornarListaVazia_QuandoNenhumaCategoriaEncontrada() {
        when(categoriaRepository.findByNomeContainingIgnoreCase("xyz")).thenReturn(List.of());

        List<CategoriaResponseDTO> resultado = categoriaService.buscarPorNome("xyz");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(categoriaRepository, times(1)).findByNomeContainingIgnoreCase("xyz");
    }

    @Test
    void existePorId_DeveRetornarTrue_QuandoCategoriaExiste() {
        when(categoriaRepository.existsById(1L)).thenReturn(true);

        boolean resultado = categoriaService.existePorId(1L);

        assertTrue(resultado);
        verify(categoriaRepository, times(1)).existsById(1L);
    }

    @Test
    void existePorId_DeveRetornarFalse_QuandoCategoriaNaoExiste() {
        when(categoriaRepository.existsById(1L)).thenReturn(false);

        boolean resultado = categoriaService.existePorId(1L);

        assertFalse(resultado);
        verify(categoriaRepository, times(1)).existsById(1L);
    }
}
