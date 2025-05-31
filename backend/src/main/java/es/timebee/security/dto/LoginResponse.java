package es.timebee.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * {@code LoginResponse} representa el objeto de datos (DTO)
 * que encapsula la respuesta enviada al cliente después
 * de un inicio de sesión exitoso.
 * <p>
 * Contiene un único campo:
 * <ul>
 *     <li>{@code token}: el JWT (JSON Web Token) generado
 *     para el usuario autenticado.</li>
 * </ul>
 * Este token es clave, porque permite que el cliente pueda
 * realizar peticiones autenticadas a otros endpoints de la aplicación
 * sin necesidad de enviar sus credenciales nuevamente.
 * <p>
 * Ejemplo práctico de respuesta:
 * <pre>
 * {
 *     "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
 * }
 * </pre>
 * <p>
 * ¡Recuerda! El token debe ser guardado de forma segura en el lado
 * del cliente (por ejemplo, en localStorage o sessionStorage)
 * para evitar problemas de seguridad.
 */
@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;
}
