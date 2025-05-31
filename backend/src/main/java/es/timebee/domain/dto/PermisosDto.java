package es.timebee.domain.dto;

import es.timebee.domain.enumeration.EstadoPermiso;
import es.timebee.domain.enumeration.TipoPermiso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * {@code PermisosDto} es un Data Transfer Object (DTO)
 * que representa un permiso solicitado por un trabajador
 * dentro del sistema TimeBee.
 * <p>
 * Este objeto contiene todos los detalles relevantes de un permiso:
 * su tipo, la fecha, el número de horas, el trabajador asociado
 * y el estado actual (pendiente, aprobado o rechazado).
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class PermisosDto implements Serializable {

    private static  final long serialVersionUID = 2317291338647522255L;

    /** El identificador único del permiso. */
    private Long id;

    /** El tipo de permiso solicitado (enum {@code TipoPermiso}). */
    private TipoPermiso permiso;

    /** El trabajador que ha solicitado el permiso (DTO embebido). */
    private TrabajadorDto trabajador;

    /** La fecha del permiso solicitado. */
    private LocalDate fecha;

    /** La cantidad de horas solicitadas. */
    private float hora;

    /** El estado actual del permiso (enum {@code EstadoPermiso}). */
    private EstadoPermiso estado;

    /**
     * Constructor vacío requerido por frameworks de serialización/deserialización.
     */
    public PermisosDto() {
        //Constructor
    }
}
