package com.API_de_Categorias.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter

public class ErroResponse {

    private int status;
    private String mensagem;
    private LocalDateTime timestamp;
    private Map<String, String> erros;


    public ErroResponse(int status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
        this.timestamp = LocalDateTime.now();
    }

    public ErroResponse(int status,
                        String mensagem,
                        Map<String,
                                String> erros) {

        this.status = status;
        this.mensagem = mensagem;
        this.erros = erros;
        this.timestamp = LocalDateTime.now();
    }
}
