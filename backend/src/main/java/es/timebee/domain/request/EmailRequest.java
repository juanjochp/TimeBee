package es.timebee.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * {@code EmailRequest} representa una solicitud simple que contiene solo un campo:
 * la dirección de correo electrónico.
 * <p>
 * Esta clase es muy útil en operaciones donde necesitamos validar o procesar
 * únicamente un email, como por ejemplo para recuperar contraseñas,
 * enviar notificaciones o realizar verificaciones.
 * <p>
 * Incluye validación para asegurarse de que el campo no venga vacío.
 */
@Getter
@Setter
public class EmailRequest implements Serializable {

    private static final long serialVersionUID = 3821779778355260032L;

    /**
     * El correo electrónico del usuario.
     * <p>
     * Este campo no puede estar vacío, gracias a la validación {@link NotBlank}.
     */
    @NotBlank
    private String email;
}
