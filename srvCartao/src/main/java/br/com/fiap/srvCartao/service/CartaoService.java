package br.com.fiap.srvCartao.service;

import br.com.fiap.srvCartao.model.Cartao;

import java.util.List;

public interface CartaoService {

    Cartao cadastrarCartao(Cartao cartao);
    Cartao obterCartaoPorNumero(String numero);
    List<Cartao> buscarCartaoPorCpf(String cpf);
}
