package br.com.fiap.srvCartao.controller;

import br.com.fiap.srvCartao.model.Cartao;
import br.com.fiap.srvCartao.service.CartaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Obtem os cartões cadastrados efetuando a busca por Cpf", method = "GET")
    public ResponseEntity<List<Cartao>> buscarCartaoPorCpf(@PathVariable String cpf) {
        var cartao = cartaoService.buscarCartaoPorCpf(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(cartao);
    }

    @GetMapping("/numero/{numero}")
    @Operation(summary = "Obtem o cartão cadastrado efetuando a busca por Número de Cartão", method = "GET")
    public ResponseEntity<Cartao> obterCartaoPorNumero(@PathVariable String numero) {
        var cartao = cartaoService.obterCartaoPorNumero(numero);
        return ResponseEntity.status(HttpStatus.OK).body(cartao);
    }

    @PutMapping("/{numero}")
    @Operation(summary = "Atualiza dados de cartão cadastrado efetuando a busca por Número de Cartão", method = "PUT")
    public ResponseEntity<Cartao> atualizarCartao(@PathVariable String numero, @Valid @RequestBody Cartao cartao) {
        Cartao atualizado = cartaoService.atualizarCartao(numero, cartao);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{numero}")
    @Operation(summary = "Deleta cartão cadastrado efetuando a busca por Número de Cartão", method = "DELETE")
    public ResponseEntity<Void> deletarCartao(@PathVariable String numero) {
        cartaoService.deletarCartao(numero);
        return ResponseEntity.noContent().build();
    }

}
