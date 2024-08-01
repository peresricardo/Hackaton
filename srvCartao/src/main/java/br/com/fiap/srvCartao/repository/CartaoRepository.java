package br.com.fiap.srvCartao.repository;

import br.com.fiap.srvCartao.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CartaoRepository extends JpaRepository<Cartao, UUID> {
    @Query("select count(numero) from Cartao where cpf = ?1")
    int countCartaoByCpf(String cpf);
    Cartao findByNumero(String numero);
    List<Cartao> findByCpf(String cpf);
}
