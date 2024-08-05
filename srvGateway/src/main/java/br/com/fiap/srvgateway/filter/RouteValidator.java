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

            "/api/cliente/v3/api-docs/**",
            "/api/cliente/api-docs/**",
            "/api/cliente/swagger-resources/**",
            "/api/cliente/swagger-ui/**",
            "/api/cliente/swagger-ui/swagger-initializer.js",
            "/api/cliente/swagger-ui/swagger-ui.css",
            "/api/cliente/swagger-ui/swagger-ui.css.map",
            "/api/cliente/swagger-ui/swagger-ui-bundle.js.map",
            "/api/cliente/swagger-ui/swagger-ui-bundle.js",
            "/api/cliente/swagger-ui/swagger-ui-standalone-preset.js",
            "/api/cliente/swagger-ui/swagger-ui-standalone-preset.js.map",
            "/api/cliente/swagger-ui/swagger-ui/index.css",
            "/api/cliente/swagger-ui/favicon-32x32.png",
            "/api/cliente/api-docs/swagger-config",
            "/api/cliente/api-docs",
            "/api/cliente/swagger-ui/index.html",
            "/api/cliente/swagger-ui/index.html/**",

            "/api/cartao/v3/api-docs/**",
            "/api/cartao/api-docs/**",
            "/api/cartao/swagger-resources/**",
            "/api/cartao/swagger-ui/**",
            "/api/cartao/swagger-ui/swagger-initializer.js",
            "/api/cartao/swagger-ui/swagger-ui.css",
            "/api/cartao/swagger-ui/swagger-ui.css.map",
            "/api/cartao/swagger-ui/swagger-ui-bundle.js.map",
            "/api/cartao/swagger-ui/swagger-ui-bundle.js",
            "/api/cartao/swagger-ui/swagger-ui-standalone-preset.js",
            "/api/cartao/swagger-ui/swagger-ui-standalone-preset.js.map",
            "/api/cartao/swagger-ui/swagger-ui/index.css",
            "/api/cartao/swagger-ui/favicon-32x32.png",
            "/api/cartao/api-docs/swagger-config",
            "/api/cartao/api-docs",
            "/api/cartao/swagger-ui/index.html",
            "/api/cartao/swagger-ui/index.html/**",

            "/api/pagamentos/v3/api-docs/**",
            "/api/pagamentos/api-docs/**",
            "/api/pagamentos/swagger-resources/**",
            "/api/pagamentos/swagger-ui/**",
            "/api/pagamentos/swagger-ui/swagger-initializer.js",
            "/api/pagamentos/swagger-ui/swagger-ui.css",
            "/api/pagamentos/swagger-ui/swagger-ui.css.map",
            "/api/pagamentos/swagger-ui/swagger-ui-bundle.js.map",
            "/api/pagamentos/swagger-ui/swagger-ui-bundle.js",
            "/api/pagamentos/swagger-ui/swagger-ui-standalone-preset.js",
            "/api/pagamentos/swagger-ui/swagger-ui-standalone-preset.js.map",
            "/api/pagamentos/swagger-ui/swagger-ui/index.css",
            "/api/pagamentos/swagger-ui/favicon-32x32.png",
            "/api/pagamentos/api-docs/swagger-config",
            "/api/pagamentos/api-docs",
            "/api/pagamentos/swagger-ui/index.html",
            "/api/pagamentos/swagger-ui/index.html/**",

            "/api/auth/v3/api-docs/**",
            "/api/auth/api-docs/**",
            "/api/auth/swagger-resources/**",
            "/api/auth/swagger-ui/**",
            "/api/auth/swagger-ui/swagger-initializer.js",
            "/api/auth/swagger-ui/swagger-ui.css",
            "/api/auth/swagger-ui/swagger-ui.css.map",
            "/api/auth/swagger-ui/swagger-ui-bundle.js.map",
            "/api/auth/swagger-ui/swagger-ui-bundle.js",
            "/api/auth/swagger-ui/swagger-ui-standalone-preset.js",
            "/api/auth/swagger-ui/swagger-ui-standalone-preset.js.map",
            "/api/auth/swagger-ui/swagger-ui/index.css",
            "/api/auth/swagger-ui/favicon-32x32.png",
            "/api/auth/api-docs/swagger-config",
            "/api/auth/api-docs",
            "/api/auth/swagger-ui/index.html",
            "/api/auth/swagger-ui/index.html/**",

            "/users/swagger-ui.html",
            "/users/api-docs"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}