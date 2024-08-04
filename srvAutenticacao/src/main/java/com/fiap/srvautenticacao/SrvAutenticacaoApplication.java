package com.fiap.srvautenticacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.fiap.srvautenticacao.model") // Ajuste o pacote conforme necessário
@EnableJpaRepositories(basePackages = "com.fiap.srvautenticacao.repository")
@EnableEurekaServer
@EnableDiscoveryClient
public class SrvAutenticacaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrvAutenticacaoApplication.class, args);
    }

}
