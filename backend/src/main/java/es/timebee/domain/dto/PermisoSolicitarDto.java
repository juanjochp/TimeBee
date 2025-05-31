package es.timebee.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import es.timebee.domain.enumeration.TipoPermiso;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * {@code PermisoSolicitarDto} es un Data Transfer Object (DTO)
 * que encapsula la información necesaria para que un trabajador
 * pueda solicitar un nuevo permiso en el sistema TimeBee.
 * <p>
 * Este objeto es enviado desde el cliente cuando se crea una nueva solicitud
 * de permiso, indicando el tipo de permiso, la fecha y el número de horas solicitadas.
 */
@Data
public class PermisoSolicitarDto {

    /** El tipo de permiso solicitado (enum {@code TipoPermiso}, no puede ser nulo). */
    @NotNull TipoPermiso permiso;

    /** La fecha del permiso solicitado (formato yyyy-MM-dd, no puede ser nula). */
    @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate fecha;

    /** La cantidad de horas solicitadas para el permiso (no puede ser nula). */
    @NotNull float hora;
}
