package br.com.fiap.srvgateway.filter;


import br.com.fiap.srvgateway.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        log.info("Entrando no filtro do Gateway");
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    log.info("Cabeçalho Authorization está ausente");
                    throw new AuthenticationCredentialsNotFoundException("Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    String user = jwtUtil.validateToken(authHeader);
                    if (user.isEmpty()) {
                        log.info("Token inválido");
                        throw new AccessDeniedException("Invalid token");
                    }
                    log.info("Usuário após validação: {}", user);
                } catch (Exception e) {
                    log.info("Erro ao validar o token", e);
                    throw new AccessDeniedException("Invalid token");
                }
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {}
}