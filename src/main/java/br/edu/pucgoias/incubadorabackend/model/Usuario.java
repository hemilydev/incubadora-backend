package br.edu.pucgoias.incubadorabackend.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidade que representa um Usuário do sistema.
 * Mapeada para a tabela 'usuarios' no banco de dados.
 */
@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome completo do usuário */
    @Column(nullable = false)
    private String nome;

    /** Email usado para login — deve ser único */
    @Column(nullable = false, unique = true)
    private String email;

    /** Senha armazenada com criptografia (BCrypt) */
    @Column(nullable = false)
    private String senha;
}