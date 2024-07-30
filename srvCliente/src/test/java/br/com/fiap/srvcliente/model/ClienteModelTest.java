package br.com.fiap.srvcliente.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.fiap.srvcliente.dto.ClienteDto;
import org.junit.jupiter.api.Test;

public class ClienteModelTest {

    @Test
    public void devePermitirUmnovoCadastroClienteModel() {
        ClienteDto clienteDto = new ClienteDto(
                "11111111111",
                "Cliente Test 001",
                "clientetest001@example.com",
                "+55 11 9111111-11",
                "Rua Teste, 45",
                "Cidade Teste",
                "Estado Teste",
                "00000-000",
                "Brasil"
        );
        ClienteModel clienteModel = new ClienteModel(clienteDto);
        assertEquals("11111111111", clienteModel.getCpf());
        assertEquals("Cliente Test 001", clienteModel.getNome());
        assertEquals("Rua Teste, 45", clienteModel.getRua());
    }
}
