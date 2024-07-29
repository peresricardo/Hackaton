package br.com.fiap.srvcliente.service.serviceImpl;

import br.com.fiap.srvcliente.dto.ClienteDto;
import br.com.fiap.srvcliente.exception.MensagemFoundException;
import br.com.fiap.srvcliente.exception.MensagemNotFoundException;
import br.com.fiap.srvcliente.model.ClienteModel;
import br.com.fiap.srvcliente.repository.ClienteRepository;
import br.com.fiap.srvcliente.service.ClienteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository repository;

    public ClienteServiceImpl(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClienteDto cadastrarCliente(ClienteDto clienteDto) {
        if (repository.existsByCpf(clienteDto.cpf())) {
            throw new MensagemFoundException("Cliente já cadastrado com esse cpf.");
        }
        ClienteModel entity = new ClienteModel(clienteDto);
        entity = repository.save(entity);
        return ClienteDto.fromEntity(entity);
    }

    @Override
    public ClienteDto atualizarCliente(ClienteDto clienteDto) {
        verificaClienteExistente(clienteDto.cpf());
        ClienteModel entity = new ClienteModel(clienteDto);
        repository.save(entity);
        return clienteDto.fromEntity(entity);
    }

    @Override
    public boolean excluirCliente(ClienteDto clienteDto) {
        verificaClienteExistente(clienteDto.cpf());
        var cliente = repository.findByCpf(clienteDto.cpf());
        var dto = ClienteDto.fromEntity(cliente);
        ClienteModel entity = new ClienteModel(dto);
        try {
            repository.delete(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Page<ClienteDto> listarClientes(Pageable pageable) {
        Page<ClienteModel> lista = repository.findAll(pageable);
        return lista.map(ClienteDto::fromEntity);
    }

    @Override
    public ClienteDto buscarClientePorCpf(String cpf) {
        verificaClienteExistente(cpf);
        var cliente = repository.findByCpf(cpf);
        return ClienteDto.fromEntity(cliente);
    }

    @Override
    public boolean existeClientePorCpf(String cpf) {
        return repository.existsByCpf(cpf);
    }

    private void verificaClienteExistente(String cpf) {
        if (!repository.existsByCpf(cpf)) {
            throw new MensagemNotFoundException("Cliente não encontrado com esse cpf.");
        }
    }
}
