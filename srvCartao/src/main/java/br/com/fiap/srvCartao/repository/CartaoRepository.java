package br.com.fiap.srvCartao.repository;

import br.com.fiap.srvCartao.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartaoRepository extends JpaRepository<Cartao, UUID> {
}
