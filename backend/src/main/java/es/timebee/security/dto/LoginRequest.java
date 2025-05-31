package es.timebee.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * {@code LoginRequest} representa el objeto de datos (DTO)
 * que encapsula la información necesaria para realizar
 * una solicitud de inicio de sesión en el sistema.
 * <p>
 * Contiene dos campos esenciales:
 * <ul>
 *     <li>{@code email}: el correo electrónico del usuario.</li>
 *     <li>{@code password}: la contraseña asociada.</li>
 * </ul>
 * Ambos campos están anotados con {@link NotBlank}, lo que significa
 * que no pueden estar vacíos ni ser solo espacios en blanco.
 * Si se envían vacíos, el sistema lanzará un mensaje claro
 * indicando la obligación de completarlos.
 * <p>
 * Este DTO es usado principalmente en el endpoint:
 * {@code POST /auth/login}, donde se reciben las credenciales
 * para autenticar al usuario.
 * <p>
 * Ejemplo práctico de uso:
 * <pre>
 * {
 *     "email": "usuario@empresa.com",
 *     "password": "claveSegura"
 * }
 * </pre>
 * <p>
 * Aunque parezca sencillo, este objeto es fundamental para proteger el acceso a la aplicación.
 */
@Data
public class LoginRequest {
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
