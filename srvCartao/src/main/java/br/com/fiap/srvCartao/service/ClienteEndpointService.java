package br.com.fiap.srvCartao.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente-service", url = "http://srv-cliente:9510/api/cliente")
public interface ClienteEndpointService {

    @GetMapping("/existeCliente/{cpf}")
    ResponseEntity<Boolean> existeCliente(@PathVariable String cpf);
}
