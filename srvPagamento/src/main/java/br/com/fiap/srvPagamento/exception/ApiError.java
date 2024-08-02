package br.com.fiap.srvPagamento.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import java.util.List;

import java.time.LocalDateTime;

@Builder
public record ApiError(
        @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
        LocalDateTime timestamp,
        Integer code,
        String status,
        List<String> erros)
{}
