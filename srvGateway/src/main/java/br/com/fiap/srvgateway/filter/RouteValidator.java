package br.com.fiap.srvgateway.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.function.Predicate;

@Configuration
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/auth/register",
            "/api/auth/login",

            "/api/cliente/swagger-ui/**",
            "/api/cliente/swagger-ui.html",
            "/api/cliente/api-docs",

            "/api/cartao/swagger-ui/**",
            "/api/cartao/swagger-ui.html",
            "/api/cartao/api-docs",

            "/api/pagamentos/swagger-ui/**",
            "/api/pagamentos/swagger-ui.html",
            "/api/pagamentos/api-docs",

            "/auth/swagger-ui/**",
            "/auth/swagger-ui.html",
            "/auth/api-docs",

            "/users/swagger-ui.html",
            "/users/api-docs"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}