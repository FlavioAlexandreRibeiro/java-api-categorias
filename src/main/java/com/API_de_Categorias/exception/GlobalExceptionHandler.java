package com.API_de_Categorias.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CategoriaNaoEncontradaException.class)
    public ResponseEntity<ErroResponse> tratarCategoriaNaoEncontrada(
            CategoriaNaoEncontradaException ex) {

        ErroResponse erro = new ErroResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }


    @ExceptionHandler(CategoriaDuplicadaException.class)
    public ResponseEntity<ErroResponse> tratarCategoriaDuplicada(
            CategoriaDuplicadaException ex) {

        ErroResponse erro = new ErroResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(erro);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarErroValidacao(
            MethodArgumentNotValidException ex) {

        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(erro ->
                        erros.put(
                                erro.getField(),
                                erro.getDefaultMessage()
                        )
                );

        ErroResponse resposta = new ErroResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Dados inválidos.",
                erros
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resposta);
    }
}
