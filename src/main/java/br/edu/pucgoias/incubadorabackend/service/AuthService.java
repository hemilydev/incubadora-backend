package br.edu.pucgoias.incubadorabackend.service;

import br.edu.pucgoias.incubadorabackend.dto.LoginRequest;
import br.edu.pucgoias.incubadorabackend.dto.LoginResponse;
import br.edu.pucgoias.incubadorabackend.model.Usuario;
import br.edu.pucgoias.incubadorabackend.repository.UsuarioRepository;
import br.edu.pucgoias.incubadorabackend.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service de autenticação.
 * Contém a lógica de login e cadastro de usuários.
 */
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Realiza o login do usuário.
     * Verifica email e senha, e retorna um token JWT se as credenciais forem válidas.
     */
    public LoginResponse login(LoginRequest request) {
        // Busca o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se a senha está correta
        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        // Gera o token JWT
        String token = jwtUtil.gerarToken(usuario.getEmail());

        return new LoginResponse(token, usuario.getNome());
    }

    /**
     * Cadastra um novo usuário no sistema.
     * A senha é criptografada antes de salvar no banco.
     */
    public Usuario cadastrar(Usuario usuario) {
        // Verifica se o email já está em uso
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        // Criptografa a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return usuarioRepository.save(usuario);
    }
}