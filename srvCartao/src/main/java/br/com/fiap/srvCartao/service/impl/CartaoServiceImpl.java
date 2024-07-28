package br.com.fiap.srvCartao.service.impl;

import br.com.fiap.srvCartao.model.Cartao;
import br.com.fiap.srvCartao.repository.CartaoRepository;
import br.com.fiap.srvCartao.service.CartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartaoServiceImpl implements CartaoService {

    private final CartaoRepository cartaoRepository;

    @Override
    public Cartao cadastrarCartao(Cartao cartao) {
        return cartaoRepository.save(cartao);
    }
}
