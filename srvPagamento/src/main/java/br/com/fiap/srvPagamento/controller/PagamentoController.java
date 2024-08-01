package br.com.fiap.srvPagamento.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagamentos")
@Tag(name = "Gestão de Pagamentos", description = "Controller para manutenção dos  pagamentos da nossa aplicação")
public class PagamentoController {
}
