package br.com.fiap.srvPagamento.service;

import br.com.fiap.srvPagamento.dto.PagamentoPorClienteDto;
import br.com.fiap.srvPagamento.model.Pagamento;
import java.util.List;

public interface PagamentoService {

    Pagamento cadastrarPagamento(Pagamento pagamento);
    List<PagamentoPorClienteDto> listaPagamentosPorCliente(String cpf);

}
