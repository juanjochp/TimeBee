package es.timebee.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * {@code FichajesDto} es un Data Transfer Object (DTO)
 * que representa un registro completo de fichaje en el sistema TimeBee.
 * <p>
 * Este objeto contiene todos los detalles necesarios para visualizar
 * o transportar la información de un fichaje, incluyendo el trabajador,
 * la empresa asociada, y las marcas de inicio y fin.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class FichajesDto implements Serializable {

    private static  final long serialVersionUID = 2124109948331583435L;

    /** El identificador único del fichaje. */
    private Long id;

    /** El trabajador asociado a este fichaje (DTO embebido). */
    private TrabajadorDto trabajador;

    /** La empresa asociada a este fichaje (DTO embebido). */
    private EmpresaDto empresa;

    /** La fecha y hora en que comenzó el fichaje. */
    private LocalDateTime fechaInicio;

    /** La fecha y hora en que terminó el fichaje (puede ser nula si no se ha cerrado). */
    private LocalDateTime fechaFin;

    /**
     * Constructor vacío requerido por frameworks de serialización/deserialización.
     */
    public FichajesDto() {
        //Constructor
    }
}
