package com.fiap.srvautenticacao.controller;

import com.fiap.srvautenticacao.model.User;
import com.fiap.srvautenticacao.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
//@Tag(name = "Usuário", description = "Endpoint para manutenção de usuários")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping
    //@Operation(summary = "Obtem todos os usuários cadastrados", method = "GET")
    public ResponseEntity getAllUsers(){
        List<User> users = this.repository.findAll();

        return ResponseEntity.ok(users);
    }

}
