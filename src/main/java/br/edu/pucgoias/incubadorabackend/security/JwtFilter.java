package br.edu.pucgoias.incubadorabackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Filtro JWT — intercepta todas as requisições HTTP.
 * Se encontrar um token válido no cabeçalho Authorization,
 * autentica o usuário automaticamente.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Pega o cabeçalho Authorization da requisição
        String authHeader = request.getHeader("Authorization");

        // Verifica se o cabeçalho existe e começa com "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            // Extrai o token removendo o prefixo "Bearer "
            String token = authHeader.substring(7);

            // Valida o token e autentica o usuário
            if (jwtUtil.tokenValido(token)) {
                String email = jwtUtil.extrairEmail(token);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Registra o usuário como autenticado no contexto do Spring Security
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // Continua o fluxo da requisição
        filterChain.doFilter(request, response);
    }
}