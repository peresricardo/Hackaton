package br.com.fiap.srvPagamento.service.impl;

import br.com.fiap.srvPagamento.exception.MensagemNotFoundException;
import br.com.fiap.srvPagamento.model.Pagamento;
import br.com.fiap.srvPagamento.repository.PagamentoRepository;
import br.com.fiap.srvPagamento.client.ClienteEndpointService;
import br.com.fiap.srvPagamento.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagamentoServiceImpl implements PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final ClienteEndpointService clienteService;

    @Override
    public Pagamento cadastrarPagamento(Pagamento pagamento) {
        if (!verificaClienteExiste(pagamento.getCpf())) {
            throw new MensagemNotFoundException("Cliente não encontrado");
        }

        //return pagamentoRepository.save(pagamento);
        return null;
    }

    private Boolean verificaClienteExiste(String cpf) {
        try {
            return clienteService.existeCliente(cpf).getBody();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao acessar endopoint cliente.");
        }
    }

}
