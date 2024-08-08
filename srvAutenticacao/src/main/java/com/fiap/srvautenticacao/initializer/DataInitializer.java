package com.fiap.srvautenticacao.initializer;

import com.fiap.srvautenticacao.model.User;
import com.fiap.srvautenticacao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se o usuário admin já existe
        if (userRepository.findByLogin("adj2") == null) {
            User admin = new User();
            admin.setLogin("adj2");
            admin.setPassword("$2a$10$HrhOtXxuRGySGc/52in2Ae3l0HnsFvw.rr6V71D..MqbC6DYyo/Gq");
            userRepository.save(admin);
            System.out.println("Usuário admin criado com sucesso!");
        } else {
            System.out.println("Usuário admin já existe.");
        }
    }
}
