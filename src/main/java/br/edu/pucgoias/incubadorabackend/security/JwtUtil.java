package br.edu.pucgoias.incubadorabackend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utilitário para geração e validação de tokens JWT.
 * JWT = JSON Web Token — usado para autenticar o usuário sem precisar
 * guardar sessão no servidor.
 */
@Component
public class JwtUtil {

    // Chave secreta usada para assinar o token
    private static final String SECRET = "incubadora-pucgoias-secret-key-2026-segura";

    // Tempo de validade do token: 24 horas em milissegundos
    private static final long EXPIRATION = 86400000;

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    /**
     * Gera um token JWT para o email informado.
     * Chamado no momento do login.
     */
    public String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrai o email (subject) de dentro do token.
     */
    public String extrairEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Verifica se o token é válido e não está expirado.
     */
    public boolean tokenValido(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}