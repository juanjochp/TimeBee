package es.timebee.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code GeneroDto} es un Data Transfer Object (DTO)
 * que representa los detalles de un género en el sistema TimeBee.
 * <p>
 * Este objeto permite transportar la información sobre los géneros disponibles,
 * como masculino, femenino, no binario, entre otros, para que puedan ser utilizados
 * en formularios, listados o configuraciones.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneroDto {

    /** El identificador único del género. */
    private int id;

    /** El nombre descriptivo del género (p.ej. “Femenino”, “Masculino”). */
    private String nombre;

    /** El código interno asociado (normalmente el {@code name()} del enum, p.ej. “FEMENINO”). */
    private String codigo;
}
