package br.com.fiap.srvcliente.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MensagemFoundExceptionTest {
    @Test
    public void validarTeste() {
        String message = "Test message";
        MensagemFoundException exception = new MensagemFoundException(message);
        assertEquals(message, exception.getMessage());
    }
}
