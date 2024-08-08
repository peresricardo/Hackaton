package br.com.fiap.srvPagamento.service.impl;

import br.com.fiap.srvPagamento.client.CartaoEndpointService;
import br.com.fiap.srvPagamento.dto.CartaoDto;
import br.com.fiap.srvPagamento.dto.PagamentoPorClienteDto;
import br.com.fiap.srvPagamento.exception.LimiteCartaoException;
import br.com.fiap.srvPagamento.exception.MensagemNotFoundException;
import br.com.fiap.srvPagamento.model.Pagamento;
import br.com.fiap.srvPagamento.repository.PagamentoRepository;
import br.com.fiap.srvPagamento.client.ClienteEndpointService;
import br.com.fiap.srvPagamento.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoServiceImpl implements PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final ClienteEndpointService clienteService;
    private final CartaoEndpointService cartaoService;

    @Override
    public Pagamento cadastrarPagamento(Pagamento pagamento) {
        if (!verificaClienteExiste(pagamento.getCpf())) {
            throw new MensagemNotFoundException("Cliente não encontrado");
        }

        CartaoDto cartaoDto = cartaoService.cartaoPorNumero(pagamento.getNumero()).getBody();

        if (cartaoDto == null) {
            throw new MensagemNotFoundException("Cartão inexistente");
        } else if (!cartaoDto.getCpf().equals(pagamento.getCpf())) {
            throw new MensagemNotFoundException("Cartão não pertence a esse cliente");
        } else if (cartaoDto.getLimite().compareTo(pagamento.getValor()) < 0) {
            throw new LimiteCartaoException("Limite insuficiente para a compra");
        } else if (!cartaoDto.getCvv().equals(pagamento.getCvv())) {
            throw new MensagemNotFoundException("Código CVV incorreto, compra recusada");
        }

        return pagamentoRepository.save(pagamento);
    }

    @Override
    public List<PagamentoPorClienteDto> listaPagamentosPorCliente(String cpf) {
        List<PagamentoPorClienteDto> retorno = new ArrayList<>();
        PagamentoPorClienteDto pagamentoDto = null;

        List<Pagamento> pagamentos = pagamentoRepository.findAllByCpf(cpf);

        for (Pagamento p : pagamentos) {
            pagamentoDto = new PagamentoPorClienteDto();
            pagamentoDto.setValor(p.getValor());
            pagamentoDto.setMetodoPagamento("cartão de crédito");
            pagamentoDto.setDescricao("compra");
            pagamentoDto.setStatus("aprovado");

            retorno.add(pagamentoDto);
        }

        return retorno;
    }

    private Boolean verificaClienteExiste(String cpf) {
        try {
            return clienteService.existeCliente(cpf).getBody();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao acessar endopoint cliente.");
        }
    }

}
