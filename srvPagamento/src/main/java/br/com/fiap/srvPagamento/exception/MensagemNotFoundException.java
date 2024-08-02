package br.com.fiap.srvPagamento.exception;

public class MensagemNotFoundException extends RuntimeException {
    public MensagemNotFoundException(String ex) {
        super(ex);
    }
}