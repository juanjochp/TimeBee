package es.timebee.domain.dto;

import es.timebee.domain.enumeration.EstadoPermiso;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * {@code PermisoValidarDto} es un Data Transfer Object (DTO)
 * que encapsula la información necesaria para validar (aprobar o rechazar)
 * un permiso en el sistema TimeBee.
 * <p>
 * Este objeto es enviado desde el cliente cuando un administrador o empresa
 * desea cambiar el estado de una solicitud de permiso previamente hecha por un trabajador.
 */
@Data
public class PermisoValidarDto {

    /** El identificador único del permiso que se va a validar (no puede ser nulo). */
    @NotNull Long id;

    /** El nuevo estado que se asignará al permiso (enum {@code EstadoPermiso}, no puede ser nulo). */
    @NotNull EstadoPermiso estado;
}
