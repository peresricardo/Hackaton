package br.com.fiap.srvcliente.controller;

import br.com.fiap.srvcliente.dto.ClienteDto;
import br.com.fiap.srvcliente.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/cliente")
@Tag(name = "Cadastro de Clientes", description = "Endpoint para manutenção no cadastro de clientes")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Efetua a inclusão de um novo cliente", method = "POST")
    public ResponseEntity<ClienteDto> cadastrar(@Valid @RequestBody ClienteDto clienteDto) {
        var cliente = service.cadastrarCliente(clienteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @PutMapping
    @Operation(summary = "Efetua a alteração de um cliente existente", method = "PUT")
    public ResponseEntity<ClienteDto> alterarCliente (@Valid @RequestBody ClienteDto clienteDto) {
        var cliente = service.atualizarCliente(clienteDto);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @DeleteMapping
    @Operation(summary = "Efetua a exclusão de um cliente existente", method = "DELETE")
    public ResponseEntity<?> excluirCliente (@Valid @RequestBody ClienteDto clienteDto) {
        boolean excluido = service.excluirCliente(clienteDto);
        if (excluido) {
            return ResponseEntity.status(HttpStatus.OK).body("Cliente excluido com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Falha ao excluir cliente.");
        }
    }

    @GetMapping("/{cpf}")
    @Operation(summary = "Obtem um cliente cadastrado efetuando a busca por Cpf", method = "GET")
    public ResponseEntity<ClienteDto> buscarClientePorCpf(@PathVariable String cpf) {
        var cliente = service.buscarClientePorCpf(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @GetMapping("/existeCliente/{cpf}")
    @Operation(summary = "Verifica se existe um cliente cadastrado por Cpf", method = "GET")
    public ResponseEntity<Boolean> existeCliente(@PathVariable String cpf) {
        var existe = service.existeClientePorCpf(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(existe);
    }

    @GetMapping
    @Operation(summary = "Obtem uma lista de todos os clientes cadastrados", method = "GET")
    public ResponseEntity<Page<ClienteDto>> listarClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClienteDto> clientes = service.listarClientes(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }
}
