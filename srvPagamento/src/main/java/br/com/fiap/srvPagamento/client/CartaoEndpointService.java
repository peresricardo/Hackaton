package br.com.fiap.srvPagamento.client;

import br.com.fiap.srvPagamento.dto.CartaoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cartao-service", url = "http://localhost:8080/api/cartao")
public interface CartaoEndpointService {

    @GetMapping("/numero/{numero}")
    ResponseEntity<CartaoDto> cartaoPorNumero(@PathVariable String numero);
}