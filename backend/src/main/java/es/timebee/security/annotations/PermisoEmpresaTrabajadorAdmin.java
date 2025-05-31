package es.timebee.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * {@code PermisoEmpresaTrabajadorAdmin} es una anotación personalizada
 * que permite el acceso únicamente a usuarios con los roles {@code EMPRESA}, {@code TRABAJADOR} o {@code ADMIN}.
 * <p>
 * Internamente usa {@link PreAuthorize} con la expresión:
 * {@code hasAnyRole('EMPRESA', 'TRABAJADOR', 'ADMIN')}, asegurándose de que
 * solo esos tres tipos de usuario puedan ejecutar los métodos o acceder a las clases marcadas.
 * <p>
 * Ejemplo práctico de uso:
 * <pre>
 * {@literal @}PermisoEmpresaTrabajadorAdmin
 * public void funcionCompartida() {
 *     // lógica disponible para empresa, trabajador y admin
 * }
 * </pre>
 * <p>
 * Centraliza las comprobaciones de seguridad y hace que el código sea más limpio, legible y mantenible.
 * En lugar de repetir condiciones en cada método, basta con anotar.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("hasAnyRole('EMPRESA', 'TRABAJADOR', 'ADMIN')")
public @interface PermisoEmpresaTrabajadorAdmin {}
