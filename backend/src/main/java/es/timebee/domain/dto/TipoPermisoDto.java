package es.timebee.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code TipoPermisoDto} es un Data Transfer Object (DTO)
 * que representa los detalles de un tipo de permiso disponible
 * en el sistema TimeBee.
 * <p>
 * Este objeto permite transportar información sobre los diferentes tipos
 * de permisos que un trabajador puede solicitar, como vacaciones, asuntos propios,
 * permisos médicos, entre otros.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoPermisoDto {

    /** El identificador único del tipo de permiso. */
    private int id;

    /** El nombre descriptivo del tipo de permiso (p.ej. “Vacaciones”). */
    private String nombre;

    /** El código interno asociado (normalmente el {@code name()} del enum, p.ej. “VACACIONES”). */
    private String codigo;
}