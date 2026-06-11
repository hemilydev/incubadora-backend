package br.edu.pucgoias.incubadorabackend.repository;

import br.edu.pucgoias.incubadorabackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositório de Usuario.
 * Usado principalmente para buscar o usuário pelo email no momento do login.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /** Busca um usuário pelo email — usado na autenticação */
    Optional<Usuario> findByEmail(String email);
}