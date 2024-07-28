package br.com.fiap.srvCartao.repository;

import br.com.fiap.srvCartao.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, String> {
}
