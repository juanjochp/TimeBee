// src/main/java/es/timebee/security/SecurityConfig.java
package es.timebee.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * {@code SecurityConfig} es el corazón de la seguridad en TimeBee.
 * <p>
 * Aquí configuramos cómo los usuarios se autentican, cómo las peticiones son protegidas,
 * y cómo fluye la seguridad en toda la aplicación.
 *
 * Desde permitir accesos públicos hasta proteger rutas privadas, todo pasa por aquí.
 */
@Configuration
@EnableMethodSecurity // Habilita @PreAuthorize en tus anotaciones
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configura la cadena de filtros de seguridad.
     * <p>
     * Este método define qué endpoints son públicos, cuáles requieren autenticación,
     * cómo se maneja la sesión (stateless con JWT) y configura el CORS para permitir
     * el acceso desde el frontend.
     *
     * @param http el objeto HttpSecurity que permite configurar las reglas de seguridad
     * @return el filtro de seguridad configurado y listo para usar
     * @throws Exception en caso de fallo en la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable) // Desactivamos CSRF porque usamos JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/login", "/registro/empresa", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/formasjuridicas", "/generos", "/permisos").permitAll() // Estas rutas son públicas
                        .anyRequest().authenticated()
                )
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:4200")); // Permitimos solo el frontend local
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }));

        http.addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Provee el {@link AuthenticationManager} que se encarga de autenticar a los usuarios.
     *
     * @param ac la configuración de autenticación de Spring Security
     * @return el gestor de autenticación configurado
     * @throws Exception en caso de error al obtenerlo
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration ac) throws Exception {
        return ac.getAuthenticationManager();
    }

    /**
     * Define el {@link PasswordEncoder} usado para encriptar y validar contraseñas.
     * <p>
     * Aquí usamos BCrypt, un algoritmo robusto y ampliamente recomendado.
     *
     * @return el codificador de contraseñas
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
