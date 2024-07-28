package br.com.fiap.srvCartao.controller;

import br.com.fiap.srvCartao.model.Cartao;
import br.com.fiap.srvCartao.service.CartaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cartao")
@Tag(name = "Gestão de Cartões", description = "Endpoint para manutenção no cadastro de cartões de crédito")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    @Operation(summary = "Efetua a inclusão de um novo Cartão", method = "POST")
    public ResponseEntity<Cartao> cadastrarCartao(@Valid @RequestBody Cartao cartao) {
        var cartaoNovo = cartaoService.cadastrarCartao(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartaoNovo);
    }
}
