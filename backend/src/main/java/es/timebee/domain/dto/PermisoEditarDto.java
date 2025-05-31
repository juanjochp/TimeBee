package es.timebee.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import es.timebee.domain.enumeration.TipoPermiso;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * {@code PermisoEditarDto} es un Data Transfer Object (DTO)
 * que encapsula la información necesaria para editar un permiso
 * en el sistema TimeBee.
 * <p>
 * Este objeto es enviado desde el cliente cuando se desea modificar
 * un permiso existente, permitiendo actualizar el tipo de permiso,
 * la fecha solicitada y la cantidad de horas.
 */
@Data
public class PermisoEditarDto {

    /** El identificador único del permiso a editar (no puede ser nulo). */
    @NotNull Long id;

    /** El tipo de permiso solicitado (enum {@code TipoPermiso}, no puede ser nulo). */
    @NotNull TipoPermiso permiso;

    /** La fecha del permiso (en formato yyyy-MM-dd, no puede ser nula). */
    @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate fecha;

    /** La cantidad de horas solicitadas para el permiso (no puede ser nula). */
    @NotNull float hora;
}
