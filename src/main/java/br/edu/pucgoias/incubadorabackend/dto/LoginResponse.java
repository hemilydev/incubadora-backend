package br.edu.pucgoias.incubadorabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO de resposta do login.
 * Retorna o token JWT e o nome do usuário após login bem-sucedido.
 */
@Data
@AllArgsConstructor
public class LoginResponse {

    /** Token JWT gerado após autenticação */
    private String token;

    /** Nome do usuário autenticado */
    private String nome;
}