package es.timebee.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * {@code PermisoTrabajador} es una anotación personalizada
 * que asegura que solo los usuarios con el rol {@code TRABAJADOR}
 * puedan acceder al método o clase donde se use.
 * <p>
 * Internamente emplea {@link PreAuthorize} con la condición:
 * {@code hasRole('TRABAJADOR')}, filtrando el acceso únicamente para ese tipo de usuario.
 * <p>
 * Ejemplo práctico:
 * <pre>
 * {@literal @}PermisoTrabajador
 * public void verMisFichajes() {
 *     // lógica exclusiva para el trabajador
 * }
 * </pre>
 * <p>
 * Permite mantener una seguridad clara y centralizada:
 * en lugar de llenar el método con comprobaciones manuales, basta con esta anotación
 * y Spring Security se encarga del resto.
 * <p>
 * Así el código queda limpio, entendible y más fácil de mantener.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("hasRole('TRABAJADOR')")
public @interface PermisoTrabajador {}
