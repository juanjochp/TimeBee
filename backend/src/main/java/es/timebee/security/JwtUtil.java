package es.timebee.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code JwtUtil} es el corazón del manejo de tokens JWT en TimeBee.
 * <p>
 * Esta clase se encarga de generar, validar y extraer información de los tokens JWT
 * que permiten a la aplicación autenticar usuarios de manera segura y sin necesidad de
 * mantener sesiones de servidor.
 *
 * <p>Sus principales tareas son:
 * <ul>
 *     <li>Generar un token firmado con los datos del usuario.</li>
 *     <li>Extraer el username (subject) de un token dado.</li>
 *     <li>Verificar si el token es válido (usuario correcto y no expirado).</li>
 * </ul>
 *
 * <p><b>Importante:</b> ¡La clave secreta es crítica! En producción,
 * siempre debe almacenarse de forma segura (no embebida en el código).
 */
@Component
public class JwtUtil {

    // Clave secreta para firmar el token. En producción, guárdala de forma segura.
    private final String SECRET_KEY = "mi_clave_super_segura_que_tiene_mas_de_32_bytes";

    // Tiempo de expiración en milisegundos (24 horas)
    private final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    // Genera un token JWT a partir del username
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().iterator().next().getAuthority());

        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae el username (subject) del token.
     *
     * @param token el JWT del cual se quiere obtener el usuario.
     * @return el username (generalmente el email).
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Valida que el token corresponde al usuario esperado y que no ha expirado.
     *
     * @param token    el JWT a validar.
     * @param username el username esperado.
     * @return {@code true} si es válido, {@code false} en caso contrario.
     */
    public boolean validateToken(String token, String username) {
        String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    /**
     * Verifica si el token ha expirado.
     *
     * @param token el JWT a revisar.
     * @return {@code true} si ha expirado, {@code false} si sigue vigente.
     */
    private boolean isTokenExpired(String token) {
        final Date expiration = extractClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    /**
     * Extrae todos los claims (información codificada) del token.
     *
     * @param token el JWT.
     * @return el objeto {@code Claims} con la información interna del token.
     */
    protected Claims extractClaims(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()             // usa el nuevo builder API
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
