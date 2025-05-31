package es.timebee.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * {@code PermisoEmpresa} es una anotación personalizada que restringe
 * el acceso a métodos o clases únicamente a usuarios con el rol {@code EMPRESA}.
 * <p>
 * Utiliza internamente {@link PreAuthorize} para comprobar que el usuario
 * autenticado tiene el rol adecuado antes de permitir la ejecución.
 * <p>
 * Ejemplo de uso:
 * <pre>
 * {@literal @}PermisoEmpresa
 * public void metodoSoloParaEmpresas() {
 *     // lógica aquí
 * }
 * </pre>
 * <p>
 * Esta anotación ayuda a mantener el código limpio y centralizar
 * las restricciones de acceso, evitando repetir configuraciones de seguridad
 * manualmente en cada método.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("hasRole('EMPRESA')")
public @interface PermisoEmpresa {}

