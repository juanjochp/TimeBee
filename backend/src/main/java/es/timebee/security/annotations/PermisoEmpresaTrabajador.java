package es.timebee.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * {@code PermisoEmpresaTrabajador} es una anotación personalizada que restringe el acceso
 * a métodos o clases únicamente a usuarios con los roles {@code EMPRESA} o {@code TRABAJADOR}.
 * <p>
 * Usa internamente {@link PreAuthorize} con la expresión {@code hasAnyRole('EMPRESA', 'TRABAJADOR')}
 * para comprobar que el usuario autenticado tiene alguno de esos roles antes de permitirle el acceso.
 * <p>
 * Ejemplo sencillo de uso:
 * <pre>
 * {@literal @}PermisoEmpresaTrabajador
 * public void metodoAccesiblePorEmpresaYTrabajador() {
 *     // lógica aquí
 * }
 * </pre>
 * <p>
 * Evita que tengamos que escribir manualmente las comprobaciones de roles
 * en cada método, manteniendo el código más limpio, claro y menos propenso a errores.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("hasAnyRole('EMPRESA', 'TRABAJADOR')")
public @interface PermisoEmpresaTrabajador {}
