package es.timebee.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * {@code JwtAuthenticationFilter} es el guardián silencioso de TimeBee.
 * <p>
 * Este filtro intercepta cada petición HTTP para:
 * <ul>
 *     <li>Revisar si hay un token JWT válido en el encabezado {@code Authorization}.</li>
 *     <li>Si existe, valida el token y autentica al usuario dentro del contexto de seguridad de Spring.</li>
 * </ul>
 *
 * <p>Funciona extendiendo {@code OncePerRequestFilter}, lo que significa que se ejecuta
 * solo una vez por petición, garantizando eficiencia y seguridad.
 *
 * <p>Flujo básico:
 * <ol>
 *     <li>Extrae el token del header {@code Authorization}.</li>
 *     <li>Verifica el usuario dentro del token usando {@code JwtUtil}.</li>
 *     <li>Carga los detalles del usuario desde {@code UserDetailsService}.</li>
 *     <li>Si todo cuadra, configura la autenticación en {@code SecurityContextHolder}.</li>
 * </ol>
 *
 * <p>Este filtro asegura que solo los usuarios correctamente autenticados
 * puedan acceder a los recursos protegidos, manteniendo la aplicación segura y confiable.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Este método intercepta cada petición HTTP entrante.
     * <p>
     * Si encuentra un token válido, configura la autenticación en Spring Security.
     *
     * @param request  la petición HTTP.
     * @param response la respuesta HTTP.
     * @param chain    el filtro encadenado que continúa la ejecución.
     * @throws ServletException en caso de error de servlet.
     * @throws IOException      en caso de error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            username = jwtUtil.extractUsername(token);
        }

        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                auth.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }
}
