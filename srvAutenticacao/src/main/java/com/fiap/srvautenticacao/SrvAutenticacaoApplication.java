package com.fiap.srvautenticacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SrvAutenticacaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrvAutenticacaoApplication.class, args);
    }

}
