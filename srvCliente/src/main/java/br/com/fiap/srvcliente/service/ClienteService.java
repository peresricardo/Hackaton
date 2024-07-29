package br.com.fiap.srvcliente.service;

import br.com.fiap.srvcliente.dto.ClienteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {
    ClienteDto cadastrarCliente(ClienteDto clienteDto);
    ClienteDto atualizarCliente(ClienteDto clienteDto);
    boolean excluirCliente(ClienteDto clienteDto);
    Page<ClienteDto> listarClientes(Pageable pageable);
    ClienteDto buscarClientePorCpf(String cpf);
    boolean existeClientePorCpf(String cpf);
}
