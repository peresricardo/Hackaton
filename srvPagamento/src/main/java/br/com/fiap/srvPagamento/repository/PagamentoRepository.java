package br.com.fiap.srvPagamento.repository;

import br.com.fiap.srvPagamento.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {

    List<Pagamento> findAllByCpf(String cpf);

}
