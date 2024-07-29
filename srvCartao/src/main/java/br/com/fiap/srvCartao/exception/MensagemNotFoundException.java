package br.com.fiap.srvCartao.exception;

public class MensagemNotFoundException extends RuntimeException {
    public MensagemNotFoundException(String ex) {
        super(ex);
    }
}
