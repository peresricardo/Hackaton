package br.com.fiap.srvPagamento.exception;

public class LimiteCartaoException extends RuntimeException {
    public LimiteCartaoException(String ex) {
        super(ex);
    }
}