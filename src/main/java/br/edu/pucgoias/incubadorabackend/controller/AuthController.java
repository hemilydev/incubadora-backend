package br.edu.pucgoias.incubadorabackend.controller;

import br.edu.pucgoias.incubadorabackend.dto.LoginRequest;
import br.edu.pucgoias.incubadorabackend.dto.LoginResponse;
import br.edu.pucgoias.incubadorabackend.model.Usuario;
import br.edu.pucgoias.incubadorabackend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller de autenticação.
 * Gerencia as rotas de login e cadastro de usuários.
 * Rotas públicas — não precisam de token JWT.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * POST /api/auth/login
     * Recebe email e senha, retorna token JWT se as credenciais forem válidas.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/auth/cadastrar
     * Cadastra um novo usuário no sistema.
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        Usuario novo = authService.cadastrar(usuario);
        return ResponseEntity.ok(novo);
    }
}