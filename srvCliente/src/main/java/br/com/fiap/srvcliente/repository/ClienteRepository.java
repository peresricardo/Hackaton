package br.com.fiap.srvcliente.repository;

import br.com.fiap.srvcliente.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, String> {
    boolean existsByCpf(String cpf);
    ClienteModel findByCpf(String cpf);
}
