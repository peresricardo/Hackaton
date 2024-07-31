package br.com.fiap.srvcliente.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MensagemNotFoundExceptionTest {

    @Test
    public void validaTest() {
        String message = "Erro de menssagem teste";
        MensagemNotFoundException exception = new MensagemNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }
}
