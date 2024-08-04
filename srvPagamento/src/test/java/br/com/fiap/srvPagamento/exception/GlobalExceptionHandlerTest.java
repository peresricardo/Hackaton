package br.com.fiap.srvPagamento.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @Test
    public void validarTestHandlerExceptionTest() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception ex = new Exception("Test exception");

        ResponseEntity<ApiError> response = handler.handlerGeneralExceptions(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().code());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), response.getBody().status());
        assertEquals("Test exception", response.getBody().erros().get(0));
    }


    @Test
    public void validarHandlerRuntimeExceptionsTest() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        RuntimeException ex = new RuntimeException("Teste de excecao");
        ResponseEntity<ApiError> response = handler.handlerRuntimeExceptions(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().code());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), response.getBody().status());
        assertEquals("Teste de excecao", response.getBody().erros().get(0));
    }


    @Test
    public void validarMensagemNotFoundExceptionsTest() {
        MensagemNotFoundException ex = new MensagemNotFoundException("Mensagem nao encontrada");
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<ApiError> response = handler.mensagemNotFoundExceptions(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().code());
        assertEquals(HttpStatus.NOT_FOUND.name(), response.getBody().status());
        assertEquals("Mensagem nao encontrada", response.getBody().erros().get(0));
    }

    @Test
    public void validarMensagemForBiddenExceptionsTest() {
        MensagemForBidden ex = new MensagemForBidden("Mensagem proibida");
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<ApiError> response = handler.mensagemForBiddenExceptions(ex);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getBody().code());
        assertEquals(HttpStatus.FORBIDDEN.name(), response.getBody().status());
        assertEquals("Mensagem proibida", response.getBody().erros().get(0));
    }


    @Test
    public void validarMensagemFoundExceptionsTest() {
        MensagemFoundException ex = new MensagemFoundException("Mensagem nao encontrada");
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<ApiError> response = handler.mensagemFoundExceptions(ex);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.FOUND.value(), response.getBody().code());
        assertEquals(HttpStatus.FOUND.name(), response.getBody().status());
        assertEquals("Mensagem nao encontrada", response.getBody().erros().get(0));
    }


    @Test
    public void validarArgumentNotValidExceptionsTest() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
        List<FieldError> fieldErrors = List.of(fieldError);

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<ApiError> response = handler.argumentNotValidExceptions(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().erros().size());
        assertEquals("field: defaultMessage", response.getBody().erros().get(0));
    }
}