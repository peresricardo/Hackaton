package br.com.fiap.srvCartao.service.impl;

import br.com.fiap.srvCartao.exception.MensagemForBidden;
import br.com.fiap.srvCartao.exception.MensagemFoundException;
import br.com.fiap.srvCartao.exception.MensagemNotFoundException;
import br.com.fiap.srvCartao.model.Cartao;
import br.com.fiap.srvCartao.repository.CartaoRepository;
import br.com.fiap.srvCartao.service.CartaoService;
import br.com.fiap.srvCartao.service.ClienteEndpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartaoServiceImpl implements CartaoService {

    private final CartaoRepository cartaoRepository;
    private final ClienteEndpointService clienteService;

    @Override
    public Cartao cadastrarCartao(Cartao cartao) {
        if (!verificaClienteExiste(cartao.getCpf())) {
            throw new MensagemNotFoundException("Cliente não encontrado");
        }

        var entity = cartaoRepository.findByNumero(cartao.getNumero());
        if (entity != null) {
            throw new MensagemFoundException("Já existe cartão cadastrado com esse número");
        }

        if (cartaoRepository.countCartaoByCpf(cartao.getCpf()) >= 2) {
            throw new MensagemForBidden("Não é possivel cadastrar mais cartões para esse cpf. Limite máximo atingido! ");
        }

        return cartaoRepository.save(cartao);
    }

    @Override
    public Cartao obterCartaoPorNumero(String numero) {
        var entity = cartaoRepository.findByNumero(numero);
        if (entity == null) {
            throw new MensagemForBidden("Cartão não encontrado com esse número");
        }

        return entity;
    }

    private Boolean verificaClienteExiste(String cpf) {
        System.out.println("Acessando endopoint cliente");
        try {
            return clienteService.existeCliente(cpf).getBody();
        } catch (Exception e) {
            System.out.println("Falha ao acessar endopoint cliente. Erro: " + e.getMessage());
            throw new RuntimeException("Falha ao acessar endopoint cliente.");
        }
    }

}
