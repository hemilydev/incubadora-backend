package br.edu.pucgoias.incubadorabackend.config;

import br.edu.pucgoias.incubadorabackend.model.Usuario;
import br.edu.pucgoias.incubadorabackend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuração de dados iniciais.
 * Cria um usuário admin automaticamente quando a aplicação sobe,
 * caso ainda não exista nenhum usuário no banco.
 */
@Configuration
public class DataInitializer {

    /**
     * Executado automaticamente ao iniciar a aplicação.
     * Insere um usuário padrão para permitir o primeiro login.
     */
    @Bean
    public CommandLineRunner initData(UsuarioRepository usuarioRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setNome("Administrador");
                admin.setEmail("admin@incubadora.com");
                admin.setSenha(passwordEncoder.encode("admin123"));
                usuarioRepository.save(admin);
                System.out.println("Usuário admin criado: admin@incubadora.com / admin123");
            }
        };
    }
}