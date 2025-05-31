package es.timebee.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * {@code PermisoAdmin} es una anotación personalizada que restringe
 * el acceso a métodos o clases únicamente a usuarios con el rol {@code ADMIN}.
 * <p>
 * Esta anotación utiliza {@link PreAuthorize} internamente para evaluar
 * si el usuario autenticado tiene el rol adecuado antes de permitir la ejecución.
 * <p>
 * Ejemplo de uso:
 * <pre>
 * {@literal @}PermisoAdmin
 * public void metodoSoloParaAdmins() {
 *     // lógica aquí
 * }
 * </pre>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("hasRole('ADMIN')")
public @interface PermisoAdmin {}
