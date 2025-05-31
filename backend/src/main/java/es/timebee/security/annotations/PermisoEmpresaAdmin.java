package es.timebee.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * {@code PermisoEmpresaAdmin} es una anotación personalizada que permite
 * acceder a métodos o clases a usuarios que tengan el rol {@code EMPRESA} o {@code ADMIN}.
 * <p>
 * Internamente utiliza {@link PreAuthorize} con {@code hasAnyRole('EMPRESA', 'ADMIN')}
 * para verificar que el usuario autenticado cumple con al menos uno de estos roles.
 * <p>
 * Ejemplo de uso:
 * <pre>
 * {@literal @}PermisoEmpresaAdmin
 * public void metodoParaEmpresaOAdmin() {
 *     // lógica accesible solo para empresa o admin
 * }
 * </pre>
 * <p>
 * Esta anotación ayuda a reducir errores al no tener que escribir manualmente
 * la expresión de roles cada vez, manteniendo la seguridad clara y centralizada.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("hasAnyRole('EMPRESA', 'ADMIN')")
public @interface PermisoEmpresaAdmin {
}
