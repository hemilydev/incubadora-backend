package br.edu.pucgoias.incubadorabackend.dto;

import lombok.Data;

/**
 * DTO de requisição de login.
 * Representa os dados que o usuário envia para fazer login.
 */
@Data
public class LoginRequest {

    /** Email do usuário */
    private String email;

    /** Senha do usuário */
    private String senha;
}