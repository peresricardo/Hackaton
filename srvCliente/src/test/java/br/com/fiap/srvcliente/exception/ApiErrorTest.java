package br.com.fiap.srvcliente.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiErrorTest {
    @Test
    public void validarTeste() {
        LocalDateTime now = LocalDateTime.now();
        ApiError apiError = ApiError.builder()
                .timestamp(now)
                .code(400)
                .status("Bad Request")
                .erros(List.of("Error Teste 1", "Error Teste 2"))
                .build();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedTimestamp = now.format(formatter);
        assertEquals(formattedTimestamp, apiError.timestamp().format(formatter));
    }
}
